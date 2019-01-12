package cn.itcast.bos.dao;

import cn.itcast.bos.domain.base.Courier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * @author Jim-gongtiancheng
 * @create 2019 - 01 - 11 10:35
 */
public interface CourierDao extends JpaRepository<Courier,Integer>, JpaSpecificationExecutor<Courier> {

}
