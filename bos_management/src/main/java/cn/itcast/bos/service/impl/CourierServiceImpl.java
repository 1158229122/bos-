package cn.itcast.bos.service.impl;

import cn.itcast.bos.dao.CourierDao;
import cn.itcast.bos.domain.base.Courier;
import cn.itcast.bos.service.CourierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

/**
 * @author Jim-gongtiancheng
 * @create 2019 - 01 - 11 10:29
 */
@Service
public class CourierServiceImpl implements CourierService {
    @Autowired
    private CourierDao courierDao;
    @Override
    public Page<Courier> searchFindAll(Pageable pageable, Courier courier) {
        Specification<Courier> spec = new Specification<Courier>() {
            @Override
            public Predicate toPredicate(Root<Courier> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                criteriaQuery.where(criteriaBuilder.like(root.get("courierNum").as(String.class),"传智播客%" ));
                return null;
            }
        };
        Page<Courier> page = courierDao.findAll(spec, pageable);
        return page;
    }


}
