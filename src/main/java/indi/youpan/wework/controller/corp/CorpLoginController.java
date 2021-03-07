package indi.youpan.wework.controller.corp;

import cn.hutool.core.util.RandomUtil;
import com.alibaba.fastjson.JSONObject;
import indi.youpan.wework.api.WeworkLoginApi;
import indi.youpan.wework.config.WeworkConfig;
import indi.youpan.wework.pojo.Message;
import indi.youpan.wework.service.WechatService;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.security.SecurityRequirement;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.math.RoundingMode;

@Path("corp/login")
@Slf4j
@Tag(name = "企业管理")
@Produces({MediaType.APPLICATION_JSON})
@Consumes(MediaType.APPLICATION_JSON)
@SecurityRequirement(name = "apiKey")
public class CorpLoginController {
  @Inject @RestClient WeworkLoginApi weworkLoginApi;
  @Inject WeworkConfig weworkConfig;
  @Inject WechatService wechatService;

  @GET
  @Operation(summary = "返回登录二维码地址")
  public Message<String> user() {
    long now = System.currentTimeMillis();
    int suffix = RandomUtil.randomInt(100, 999);
    JSONObject keyJSON =
        weworkLoginApi.getLoginKey(
            RandomUtil.randomDouble(1, 16, RoundingMode.CEILING),
            "login_admin",
            "wwqrloginCallback_" + now,
            weworkConfig.getRedirectUrl() + now + suffix,
            1);
    log.info("key:{}", keyJSON);
    String qrCodeKey = keyJSON.getJSONObject("data").getString("qrcode_key");
    wechatService.checkQrcodeScanStatus(qrCodeKey);
    return Message.successWithStr(String.format(weworkConfig.getLoginUrl(), qrCodeKey));
  }
}
