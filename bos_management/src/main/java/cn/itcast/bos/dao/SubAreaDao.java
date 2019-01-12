package cn.itcast.bos.dao;


import cn.itcast.bos.domain.base.SubArea;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * @author Jim-gongtiancheng
 * @create 2019 - 01 - 12 14:42
 */
public interface SubAreaDao extends JpaRepository<SubArea,String>, JpaSpecificationExecutor<SubArea> {
}
