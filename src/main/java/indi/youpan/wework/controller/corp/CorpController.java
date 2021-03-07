package indi.youpan.wework.controller.corp;

import indi.youpan.wework.entity.CorpInfo;
import indi.youpan.wework.pojo.Message;
import indi.youpan.wework.service.CorpInfoService;
import indi.youpan.wework.util.CorpContext;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.security.SecurityRequirement;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import org.jboss.resteasy.annotations.jaxrs.QueryParam;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("corp")
@Slf4j
@Tag(name = "企业管理")
@Produces({MediaType.APPLICATION_JSON})
@Consumes(MediaType.APPLICATION_JSON)
@SecurityRequirement(name = "apiKey")
public class CorpController {
  @Inject CorpInfoService corpInfoService;

  @GET
  @Path("info")
  @Operation(summary = "获取公司信息，参数至少传一个")
  public Message<CorpInfo> getCorpInfo(@QueryParam String corpId, @QueryParam String encodeCorpId) {
    if (null == corpId && StringUtils.isEmpty(encodeCorpId)) {
      return Message.failure("参数不正确！");
    }
    return Message.success(corpInfoService.getCorp(corpId, encodeCorpId));
  }

  @GET
  @Path("info/current")
  @Operation(summary = "获取当前公司信息")
  public Message<CorpInfo> getCurrentCorpInfo() {
    return Message.success(corpInfoService.getCorp(CorpContext.getContext().getCorpId(), null));
  }
}
