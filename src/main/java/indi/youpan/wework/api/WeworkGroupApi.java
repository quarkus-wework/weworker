package indi.youpan.wework.api;

import com.alibaba.fastjson.JSONObject;
import indi.youpan.wework.config.ApiFilter;
import org.eclipse.microprofile.rest.client.annotation.RegisterProvider;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;
import org.jboss.resteasy.annotations.jaxrs.QueryParam;

import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;

/** 群api管理 */
@RegisterRestClient(baseUri = "https://work.weixin.qq.com")
@RegisterProvider(ApiFilter.class)
public interface WeworkGroupApi {
  /**
   * 获取群列表
   *
   * @return
   */
  @GET
  @Path("/wework_admin/customer/getGroupChatList")
  JSONObject getGroupChatList(
      @QueryParam("off_set") int offset,
      @QueryParam("limit") int limit,
      @QueryParam("random") double random,
      @QueryParam("create_ts_begin") long create_ts_begin,
      @QueryParam("create_ts_end") long create_ts_end,
      @QueryParam("keywords") String keywords,
      @QueryParam("_d2st") String _d2st);

  /** 创建新群 */
  @POST
  @Path("/wework_admin/chatGroup/createChatGroup")
  JSONObject createChatGroup(
      @FormParam("name") String name,
      @FormParam("ownervid") long ownervid,
      @FormParam("_d2st") String _d2st);
}
