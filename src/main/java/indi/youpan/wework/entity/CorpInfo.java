package indi.youpan.wework.entity;

import lombok.Data;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

import javax.persistence.*;

@Data
@Entity
@Schema(description = "公司信息")
@Table(indexes = {@Index(columnList = "corpId"), @Index(columnList = "encodeCorpId")})
public class CorpInfo {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(columnDefinition = "varchar(32) default null")
  private String d2st;

  @Column(columnDefinition = "varchar(2048) default null comment '当前登录的cookies'")
  private String currentCookies;

  @Schema(description = "原生corpId")
  @Column(columnDefinition = "varchar(64) default null comment '原生corpId'")
  private String corpId;

  @Schema(description = "openapi对应的corpId")
  @Column(columnDefinition = "varchar(64) default null comment 'openapi对应的corpId'")
  private String encodeCorpId;

  @Schema(description = "公司别名")
  private String corpAlias;

  @Schema(description = "公司全称")
  private String corpFullName;

  @Schema(description = "公司地址")
  private String corpAddress;

  @Schema(description = "公司域名")
  private String corpDomain;
}
