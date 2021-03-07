package indi.youpan.wework.service;

import cn.hutool.core.util.RandomUtil;
import com.alibaba.fastjson.JSONObject;
import indi.youpan.wework.api.WeworkGroupApi;
import indi.youpan.wework.entity.CorpInfo;
import indi.youpan.wework.repo.CorpInfoRepo;
import indi.youpan.wework.util.CorpContext;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.math.RoundingMode;

@ApplicationScoped
@Slf4j
public class CorpInfoService {
  @Inject CorpInfoRepo corpInfoRepo;
  @Inject @RestClient WeworkGroupApi weworkGroupApi;

  public void saveCorp(CorpInfo corpInfo) {
    corpInfoRepo.save(corpInfo);
  }

  /**
   * 获取企业信息
   *
   * @param corpId
   * @param encodeCorpId
   * @return
   */
  public CorpInfo getCorp(String corpId, String encodeCorpId) {
    if (null != corpId) {
      return corpInfoRepo.findByCorpId(corpId);
    }
    return corpInfoRepo.findByEncodeCorpId(encodeCorpId);
  }

  /** encodeCorpId 转换为corpId */
  public String getCorpId(String encodeCorpId) {
    if (null == encodeCorpId) {
      log.warn("corpId不能为空！");
      return null;
    }
    CorpInfo corpInfo = corpInfoRepo.findByEncodeCorpId(encodeCorpId);
    if (null == corpInfo) {
      log.warn("当前公司:{}不存在！", encodeCorpId);
      return null;
    }
    return corpInfo.getCorpId();
  }

  public CorpInfo saveCorp(String confirmCorpId, String d2st, String cookies) {
    CorpInfo corpInfo = corpInfoRepo.findByCorpId(confirmCorpId);
    if (null == corpInfo) {
      corpInfo = new CorpInfo();
    }
    corpInfo.setD2st(d2st);
    corpInfo.setCurrentCookies(cookies);
    corpInfo.setCorpId(confirmCorpId);
    corpInfoRepo.save(corpInfo);
    return corpInfo;
  }

  // @Scheduled(cron = "*/5 * * * * ?")
  void cronJob() {
    CorpInfo corpInfo = corpInfoRepo.findByCorpId("1970325063023264");
    CorpContext.getContext().setCorpId("1970325063023264");
    JSONObject groupJSON =
        weworkGroupApi.getGroupChatList(
            0,
            10,
            RandomUtil.randomDouble(1, 16, RoundingMode.CEILING),
            1499184000,
            1604804978,
            null,
            corpInfo.getD2st());
    log.info(groupJSON.toString());
  }
}
