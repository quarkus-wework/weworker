package indi.youpan.wework.api.resp;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = false)
@Data
public class AccessTokenResp extends BaseResp {
  @JsonProperty(value = "access_token")
  private String accessToken;

  @JsonProperty(value = "expires_in")
  private String expiresIn;
}
