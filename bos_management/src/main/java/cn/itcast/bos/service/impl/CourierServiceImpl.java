package cn.itcast.bos.service.impl;

import cn.itcast.bos.dao.CourierDao;
import cn.itcast.bos.dao.StandardDao;
import cn.itcast.bos.domain.base.Courier;
import cn.itcast.bos.domain.base.Standard;
import cn.itcast.bos.service.CourierService;
import com.alibaba.druid.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.Optional;

/**
 * @author Jim-gongtiancheng
 * @create 2019 - 01 - 11 10:29
 */
@Service
public class CourierServiceImpl implements CourierService {
    @Autowired
    private CourierDao courierDao;
    @Autowired
    private StandardDao standardDao;
    @Override
    public Page<Courier> searchFindAll(Pageable pageable, Courier courier) {
        Specification<Courier> spec = new Specification<Courier>() {
            @Override
            public Predicate toPredicate(Root<Courier> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                if (!StringUtils.isEmpty(courier.getCourierNum())){
                    criteriaQuery.where(criteriaBuilder.like(root.get("courierNum").as(String.class),"%"+courier.getCourierNum()+"%" ));
                }
                if (!StringUtils.isEmpty(courier.getCompany())){
                    criteriaQuery.where(criteriaBuilder.like(root.get("company").as(String.class),"%"+courier.getCompany()+"%"));
                }
                if (!StringUtils.isEmpty(courier.getType())){
                    criteriaQuery.where(criteriaBuilder.like(root.get("type").as(String.class),"%"+courier.getType()+"%"));
                }
                if (courier.getStandard()!=null&&courier.getStandard().getId()!=null){
                    criteriaQuery.where(criteriaBuilder.like(root.get("standard").get("id").as(String.class),"%"+courier.getStandard().getId()+"%"));
                }
                return criteriaQuery.getRestriction();
            }
        };
        Page<Courier> page = courierDao.findAll(spec,pageable);
        return page;
    }

    @Override
    public void saveCourier(Courier courier) {
        courierDao.save(courier);
    }

    @Override
    public void deleteByIds(Integer[] ids) {
        for (Integer id : ids) {
            Optional<Courier> optionalCourier = courierDao.findById(id);
            Courier courier = optionalCourier.get();
            courier.setDeltag(Character.valueOf('0'));
            courierDao.save(courier);
        }

    }

    @Override
    public List<Standard> getStandardAll() {
        return standardDao.findAll();
    }

    @Override
    public void updateStatus(Integer[] ids) {
        for (Integer id : ids) {
            Optional<Courier> byId = courierDao.findById(id);
            if (byId==null||byId.get()==null){
                throw  new RuntimeException("没有找到要操作的类++++"+id);
            }
            Courier courier = byId.get();
            courier.setDeltag(Character.valueOf('1'));
            courierDao.save(courier);
        }
    }


}
