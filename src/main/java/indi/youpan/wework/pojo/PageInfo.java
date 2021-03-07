/**
 * Project Name:liz-common-utils File Name:PageInfo.java Package Name:com.gemii.lizcloud.common.data
 * Date:Sep 29, 20162:22:06 PM Copyright (c) 2016, chenxj All Rights Reserved.
 */
package indi.youpan.wework.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

import java.io.Serializable;

/**
 * ClassName:PageInfo <br>
 * Function: TODO ADD FUNCTION. <br>
 * Reason: TODO ADD REASON. <br>
 * Date: Sep 29, 2016 2:22:06 PM <br>
 *
 * @author chenxj
 * @version
 * @since JDK 1.8
 * @see
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
@Schema(description = "页数信息")
public class PageInfo implements Serializable {
  private static final long serialVersionUID = -7644066325412964122L;

  @Schema(title = "当前页，从1开始")
  private long currentPage = 1;

  @Schema(title = "总页数")
  private Long totalPage;

  @Schema(title = "页面大小")
  private long pageSize = 10;

  @Schema(title = "总行数")
  private long totalRecords;

  public long getTotalPage() {
    if (0 == totalRecords) {
      return 0;
    }
    long pages = totalRecords / pageSize;
    if (totalRecords % pageSize != 0) {
      pages++;
    }
    return pages;
  }
}
