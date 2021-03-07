package indi.youpan.wework.api.resp;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class DepartmentResp extends BaseResp {
  @JsonProperty("department")
  List<DepartmentPojo> departments;
}
