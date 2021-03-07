package indi.youpan.wework.api;

import com.alibaba.fastjson.JSONObject;
import indi.youpan.wework.config.ApiFilter;
import org.eclipse.microprofile.rest.client.annotation.RegisterProvider;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;
import org.jboss.resteasy.annotations.jaxrs.QueryParam;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

/** 企微web后台获取二维码、公司信息等api */
@RegisterRestClient(baseUri = "https://work.weixin.qq.com/")
@RegisterProvider(ApiFilter.class)
public interface WeworkLoginApi {
  /** 获取二维码KEY */
  @GET
  @Path("/wework_admin/wwqrlogin/get_key")
  @Produces("application/json")
  JSONObject getLoginKey(
      @QueryParam("r") double r,
      @QueryParam("login_type") String login_type,
      @QueryParam("callback") String callback,
      @QueryParam("redirect_uri") String redirect_uri,
      @QueryParam("crossorigin") int crossorigin);
  /**
   * 检查扫码状态
   *
   * @param qrcode_key 获取二维码后会有一个qrcode_key
   */
  @GET
  @Path("/wework_admin/wwqrlogin/check")
  @Produces("application/json")
  JSONObject check(
      @QueryParam("qrcode_key") String qrcode_key,
      @QueryParam("status") String status,
      @QueryParam("r") double f);

  /**
   * 用来获取 cookie与_d2st 后续企微接口调用都需要用到这两个参数
   *
   * @param pagekey 当前时间
   * @param code 从扫描二维码返回的数据里获取
   * @param wwqrlogin 固定 1
   * @param qrcode_key 获取二维码后会有一个qrcode_key
   * @param auth_source 固定 SOURCE_FROM_WEWORK
   */
  @GET
  @Path("/wework_admin/loginpage_wx")
  @Produces("application/json")
  Response loginpage_wx(
      @QueryParam("pagekey") long pagekey,
      @QueryParam("code") String code,
      @QueryParam("wwqrlogin") int wwqrlogin,
      @QueryParam("qrcode_key") String qrcode_key,
      @QueryParam("auth_source") String auth_source);

  /**
   * 用于获取微信登陆后的页面，能从中获取当前企业解密后的corpId（开放平台的corpId）
   *
   * @return
   */
  @GET
  @Path("wework_admin/frame")
  String frame();
}
