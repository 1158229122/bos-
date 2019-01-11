package cn.itcast.bos.service.impl;

import cn.itcast.bos.dao.AdminDao;
import cn.itcast.bos.domain.base.Admin;
import cn.itcast.bos.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Jim-gongtiancheng
 * @create 2019 - 01 - 11 11:40
 */
@Service
public class AdminServiceImpl implements AdminService {
    @Autowired
    private AdminDao adminDao;
    @Override
    public List<Admin> login(Admin loginAdmin) {
        return adminDao.findByUsernameAndPassword(loginAdmin.getUsername(),loginAdmin.getPassword());
    }
}
