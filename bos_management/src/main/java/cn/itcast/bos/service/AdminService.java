package cn.itcast.bos.service;

import cn.itcast.bos.domain.base.Admin;

import java.util.List;

/**
 * @author Jim-gongtiancheng
 * @create 2019 - 01 - 09 20:49
 */
public interface AdminService {
    /**
     * 登录方法
     * @param loginAdmin
     * @return
     */
    List<Admin> login(Admin loginAdmin);
}
