package cn.itcast.bos.dao;

import cn.itcast.bos.domain.base.Area;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * @author Jim-gongtiancheng
 * @create 2019 - 01 - 12 14:42
 */
public interface AreaDao extends JpaRepository<Area,String>, JpaSpecificationExecutor<Area> {
}
