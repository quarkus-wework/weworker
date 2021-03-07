package indi.youpan.wework.controller.corp;

import indi.youpan.wework.api.WeworkGroupApi;
import indi.youpan.wework.pojo.Message;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@ApplicationScoped
@Path("corp/group")
@Slf4j
@Tag(name = "群管理")
@Produces({MediaType.APPLICATION_JSON})
public class CorpGroupController {
  @Inject @RestClient WeworkGroupApi weworkGroupApi;

  @Operation(summary = "创建群", description = "")
  @Path("create")
  @POST
  @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
  public Message<Void> createGroup(
      @FormParam("name") @Parameter(description = "群名称", required = true, example = "") String name,
      @FormParam("ownervid") @Parameter(description = "群主的vid", required = true, example = "")
          long ownervid) {
    log.info("name:{},ownervid:{}", name, ownervid);
    return Message.success();
  }
}
