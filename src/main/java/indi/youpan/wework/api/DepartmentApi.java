package indi.youpan.wework.api;

import indi.youpan.wework.api.resp.DepartmentResp;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;
import org.jboss.resteasy.annotations.jaxrs.QueryParam;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

@RegisterRestClient(baseUri = "https://qyapi.weixin.qq.com/cgi-bin")
public interface DepartmentApi {
  @Path("department/list")
  @GET
  @Produces("application/json")
  DepartmentResp list(@QueryParam String access_token, @QueryParam Integer id);
}
