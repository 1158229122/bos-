package cn.itcast.bos.service.impl;

import cn.itcast.bos.dao.AreaDao;
import cn.itcast.bos.domain.base.Area;
import cn.itcast.bos.service.AreaService;
import org.apache.commons.lang3.StringUtils;
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
 * @create 2019 - 01 - 12 14:41
 */
@Service
public class AreaServiceImpl implements AreaService {
    @Autowired
    private AreaDao areaDao;
    @Override
    public void saveArea(Area area) {

        areaDao.save(area);
    }

    @Override
    public Page<Area> searchFindAll(Pageable pageable, Area area) {
        Specification<Area> spec = new Specification<Area>() {
            @Override
            public Predicate toPredicate(Root<Area> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                if (StringUtils.isNotBlank(area.getProvince())){
                    criteriaQuery.where(criteriaBuilder.like(root.get("province").as(String.class),"%"+area.getProvince()+"%" ));
                }
                if (StringUtils.isNotBlank(area.getCity())){
                    criteriaQuery.where(criteriaBuilder.like(root.get("city").as(String.class),"%"+area.getCity()+"%" ));
                }
                if (StringUtils.isNotBlank(area.getDistrict())){
                    criteriaQuery.where(criteriaBuilder.like(root.get("district").as(String.class),"%"+area.getDistrict()+"%" ));
                }
                return criteriaQuery.getRestriction();
            }
        };
        return areaDao.findAll(spec,pageable);
    }

    @Override
    public void deleteByIds(String[] ids) {
        for (String id : ids) {
            Area area = areaDao.findById(id).get();
            area.setIsDelete("0");
            areaDao.save(area);
        }
    }

    @Override
    public void updateStatus(String[] ids) {
        for (String id : ids) {
            Area area = areaDao.findById(id).get();
            area.setIsDelete("1");
            areaDao.save(area);
        }
    }
}
