package indi.youpan.wework.api;

import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

@RegisterRestClient(baseUri = "https://qyapi.weixin.qq.com/cgi-bin")
public interface UserApi {}
