package indi.youpan.wework.repo;

import indi.youpan.wework.entity.CorpInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public interface CorpInfoRepo
    extends CrudRepository<CorpInfo, Long>, JpaRepository<CorpInfo, Long> {
  CorpInfo findByCorpId(String corpId);

  CorpInfo findByEncodeCorpId(String encodeCorpId);
}
