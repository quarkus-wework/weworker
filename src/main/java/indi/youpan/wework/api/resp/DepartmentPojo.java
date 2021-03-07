package indi.youpan.wework.api.resp;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class DepartmentPojo {
  private Long id;
  private String name;

  @JsonProperty("name_en")
  private String nameEn;

  private Long parentid;
  private Long order;
}
