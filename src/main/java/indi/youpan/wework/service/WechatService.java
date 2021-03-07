package indi.youpan.wework.service;

import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.ReUtil;
import com.alibaba.fastjson.JSONObject;
import indi.youpan.wework.api.WeworkGroupApi;
import indi.youpan.wework.api.WeworkLoginApi;
import indi.youpan.wework.entity.CorpInfo;
import indi.youpan.wework.util.CorpContext;
import io.smallrye.mutiny.Uni;
import io.smallrye.mutiny.infrastructure.Infrastructure;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.core.Response;
import java.math.RoundingMode;
import java.util.Collection;
import java.util.concurrent.TimeUnit;

@ApplicationScoped
@Slf4j
public class WechatService {
  @Inject @RestClient private WeworkLoginApi weworkLoginApi;
  @Inject @RestClient private WeworkGroupApi weworkGroupApi;
  @Inject CorpInfoService corpInfoService;
  private final String corpInfoReg = "settings = \\{.*\\}"; // 用来匹配公司信息

  /** 异步检查二维码扫描状态 */
  public void checkQrcodeScanStatus(String qrCodeKey) {
    Uni.createFrom()
        .item(qrCodeKey)
        .emitOn(Infrastructure.getDefaultWorkerPool())
        .subscribe()
        .with(this::doCheckQrcodeScanStatus, Throwable::printStackTrace);
  }

  private Uni<Void> doCheckQrcodeScanStatus(String qrCodeKey) {
    log.info("开始监听二维码扫描状态:{}", qrCodeKey);
    long now = System.currentTimeMillis();
    double f = RandomUtil.randomDouble(1, 16, RoundingMode.CEILING);
    JSONObject checkJSON = weworkLoginApi.check(qrCodeKey, "QRCODE_SCAN_NEVER", f);
    log.info("监听二维码扫描状态:{}", checkJSON);
    JSONObject checkJSONData = checkJSON.getJSONObject("data");
    while (!"QRCODE_SCAN_SUCC".equalsIgnoreCase(checkJSONData.getString("status"))) {
      try {
        TimeUnit.SECONDS.sleep(2);
      } catch (Exception e) {
        log.warn(e.getMessage());
      }
      checkJSON = weworkLoginApi.check(qrCodeKey, "QRCODE_SCAN_NEVER", f);
      log.info("监听二维码扫描状态:{}", checkJSON);
      checkJSONData = checkJSON.getJSONObject("data");
    }
    log.info("二维码扫描成功:{},获取微信COOKIE", checkJSONData);
    Response response =
        weworkLoginApi.loginpage_wx(
            now, checkJSONData.getString("auth_code"), 1, qrCodeKey, "SOURCE_FROM_WEWORK");
    log.info("status:{}", response.getStatus());
    String confirmCorpId = checkJSONData.getString("confirm_corpid");
    Collection<String> cookies = response.getStringHeaders().get("set-cookie");
    String _d2st = null;
    for (String cookie : cookies) {
      // log.info(cookie);
      String[] wxCookies = cookie.split(" ");
      log.info("wxcookie:{}", wxCookies[0]);
      if (wxCookies[0].contains("d2st")) {
        _d2st = wxCookies[0].split("=")[1].substring(0, 7);
        // QyWebApiConfig.d2st = _d2st;
      }
    }
    saveCorpInfo(confirmCorpId, _d2st, StringUtils.join(cookies, ";"));
    return Uni.createFrom().voidItem();
  }

  private void saveCorpInfo(String confirmCorpId, String d2st, String cookies) {
    try {
      CorpInfo corpInfo = corpInfoService.saveCorp(confirmCorpId, d2st, cookies);
      CorpContext.getContext().setCorpId(confirmCorpId);
      Long id = corpInfo.getId();
      String frameStr = weworkLoginApi.frame();
      String groupInfoStr = ReUtil.get(corpInfoReg, frameStr, 0).split("\n")[0];
      if (StringUtils.isBlank(groupInfoStr)) {
        log.error("获取公司信息失败,corpId:{}", confirmCorpId);
        return;
      }
      groupInfoStr = groupInfoStr.substring(11);
      if (groupInfoStr.endsWith(";")) {
        groupInfoStr = groupInfoStr.substring(0, groupInfoStr.length() - 1);
      }
      log.info("corpInfo:{}", groupInfoStr);
      corpInfo = JSONObject.parseObject(groupInfoStr, CorpInfo.class);
      corpInfo.setId(id);
      corpInfo.setD2st(d2st);
      corpInfo.setCurrentCookies(cookies);
      corpInfoService.saveCorp(corpInfo);
    } catch (Exception e) {
      log.error("获取公司信息失败,corpId:{},{}", confirmCorpId, e.getMessage(), e);
      return;
    }
  }
}
