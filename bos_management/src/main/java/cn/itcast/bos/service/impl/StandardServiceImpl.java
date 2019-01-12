package cn.itcast.bos.service.impl;

import cn.itcast.bos.dao.StandardDao;
import cn.itcast.bos.domain.base.Standard;
import cn.itcast.bos.service.StanderdService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * @author Jim-gongtiancheng
 * @create 2019 - 01 - 09 20:12
 */

@Service
public class StandardServiceImpl implements StanderdService {
    @Autowired
    private StandardDao standardDao;
    @Override
    public void save(Standard standard) {
        standard.setIsDelete("1");//使用中
        standardDao.save(standard);
    }

    @Override
    public Page<Standard> findAll(Pageable pageable) {
        return standardDao.findByIsDeleteEquals(pageable,"1");
    }

    @Override
    public void deleteById(Integer[] ids) {
        for (Integer id : ids) {
            Optional<Standard> standard = standardDao.findById(id);
            Standard standard1 = standard.get();
            standard1.setIsDelete("0");
            standardDao.save(standard1);
        }
        //standardDao.deleteById(id);
    }

    @Override
    public Standard findById(Integer value) {
        return standardDao.findById(value).get();
    }
}
