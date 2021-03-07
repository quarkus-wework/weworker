package indi.youpan.wework.config;

import io.quarkus.arc.config.ConfigProperties;
import lombok.Data;

@Data
@ConfigProperties(prefix = "wework")
public class WeworkConfig {

  /** 二维码地址 */
  private String loginUrl;

  /** 重定向地址，默认企微官方后台地址 */
  private String redirectUrl;
}
