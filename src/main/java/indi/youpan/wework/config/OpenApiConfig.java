package indi.youpan.wework.config;

import org.eclipse.microprofile.openapi.annotations.Components;
import org.eclipse.microprofile.openapi.annotations.OpenAPIDefinition;
import org.eclipse.microprofile.openapi.annotations.enums.SecuritySchemeIn;
import org.eclipse.microprofile.openapi.annotations.enums.SecuritySchemeType;
import org.eclipse.microprofile.openapi.annotations.info.Contact;
import org.eclipse.microprofile.openapi.annotations.info.Info;
import org.eclipse.microprofile.openapi.annotations.security.SecurityRequirement;
import org.eclipse.microprofile.openapi.annotations.security.SecurityScheme;

import javax.ws.rs.core.Application;

@OpenAPIDefinition(
    info =
        @Info(
            title = "企微后台api",
            version = "0.0.1",
            description =
                "企微后台接口api化，方便企业调用。<br>除了企业登录之外，其他的接口均需要在header中传递解密后的corpId，可以点击Authorize填写。",
            contact = @Contact(name = "pan.you")),
    components =
        @Components(
            securitySchemes = {
              @SecurityScheme(
                  securitySchemeName = "apiKey",
                  type = SecuritySchemeType.APIKEY,
                  apiKeyName = "Encode-Corp-Id",
                  in = SecuritySchemeIn.HEADER,
                  scheme = "bearer")
            }),
    security = {@SecurityRequirement(name = "apiKey")})
public class OpenApiConfig extends Application {}
