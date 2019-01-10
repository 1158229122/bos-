package cn.itcast.bos.dao;

import cn.itcast.bos.domain.base.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

/**
 * @author Jim-gongtiancheng
 * @create 2019 - 01 - 09 20:45
 */
public interface AdminDao extends JpaRepository<Admin,Integer>, JpaSpecificationExecutor<Admin> {
    List<Admin> findByUsernameAndPassword(String username, String password);


}
