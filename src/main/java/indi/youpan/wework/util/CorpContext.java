package indi.youpan.wework.util;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

@Data
@Slf4j
public class CorpContext {
  private static final ThreadLocal<CorpContext> threadLocal = new ThreadLocal<CorpContext>();
  private String corpId;

  private CorpContext() {
    threadLocal.set(this); // 存入ThreadLocal
  }

  public static CorpContext getContext() {
    CorpContext context = threadLocal.get();
    if (null == context) {
      context = new CorpContext();
    }
    return context;
  }

  public String getCorpId() {
    if (StringUtils.isBlank(corpId)) {
      log.warn("没有corpId，调用企微后台可能会失败!");
    }
    return corpId;
  }

  public void setCorpId(String corpId) {
    log.info("corpId:{}", corpId);
    this.corpId = corpId;
  }
}
