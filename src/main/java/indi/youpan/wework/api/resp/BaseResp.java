package indi.youpan.wework.api.resp;

import lombok.Data;

@Data
public class BaseResp {
  private Integer errcode;
  private String errmsg;
}
