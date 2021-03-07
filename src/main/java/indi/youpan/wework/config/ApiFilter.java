package indi.youpan.wework.config;

import com.alibaba.fastjson.JSONObject;
import indi.youpan.wework.entity.CorpInfo;
import indi.youpan.wework.repo.CorpInfoRepo;
import indi.youpan.wework.util.CorpContext;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import javax.inject.Inject;
import javax.ws.rs.client.ClientRequestContext;
import javax.ws.rs.client.ClientRequestFilter;
import java.io.IOException;

@Slf4j
public class ApiFilter implements ClientRequestFilter {
  private static final String referer = "https://work.weixin.qq.com/";
  @Inject CorpInfoRepo corpInfoRepo;

  @Override
  public void filter(ClientRequestContext clientRequestContext) throws IOException {
    log.info(clientRequestContext.getUri().toString());
    clientRequestContext.getHeaders().add("referer", referer);
    String corpId = CorpContext.getContext().getCorpId();
    if (StringUtils.isNotBlank(corpId)) {
      CorpInfo corpInfo = corpInfoRepo.findByCorpId(corpId);
      clientRequestContext.getHeaders().add("cookie", corpInfo.getCurrentCookies());
    }
    clientRequestContext
        .getHeaders()
        .forEach(
            (x, y) -> {
              log.info("key:{},value:{}", x, JSONObject.toJSONString(y));
            });
  }
}
