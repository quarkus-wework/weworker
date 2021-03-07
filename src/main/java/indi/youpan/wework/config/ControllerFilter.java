package indi.youpan.wework.config;

import com.alibaba.fastjson.JSONObject;
import indi.youpan.wework.service.CorpInfoService;
import indi.youpan.wework.util.CorpContext;
import io.vertx.core.http.HttpServerRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import javax.inject.Inject;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.ext.Provider;

@Slf4j
@Provider
public class ControllerFilter implements ContainerRequestFilter {
  @Context UriInfo info;
  @Inject CorpInfoService corpInfoService;

  @Context HttpServerRequest request;

  @Override
  public void filter(ContainerRequestContext context) {
    final String method = context.getMethod();
    final String path = info.getPath();
    final String address = request.remoteAddress().toString();
    final String currentEncodeCorpId = request.getHeader("Encode-Corp-Id");
    if (StringUtils.isNotBlank(currentEncodeCorpId)) {
      CorpContext.getContext().setCorpId(corpInfoService.getCorpId(currentEncodeCorpId));
    }
    log.info(
        "request {} {} ,param:{},formParam:{}, from IP {}",
        method,
        path,
        JSONObject.toJSONString(request.params()),
        JSONObject.toJSONString(request.formAttributes()),
        address);
  }
}
