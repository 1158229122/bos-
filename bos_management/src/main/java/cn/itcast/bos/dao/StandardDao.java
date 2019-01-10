package cn.itcast.bos.dao;

import cn.itcast.bos.domain.base.Standard;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
/**
 * @author Jim-gongtiancheng
 * @create 2019 - 01 - 09 20:12
 */
public interface StandardDao extends JpaRepository<Standard,Integer>, JpaSpecificationExecutor<Standard> {
    /**
     * 查询所有不包含删除的
     * @param pageable
     * @param delete 1正常 0删除
     * @return
     */
    Page<Standard> findByIsDeleteEquals(Pageable pageable,String delete);
}
