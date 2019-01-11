package cn.itcast.bos.controller;

import cn.itcast.bos.domain.base.Courier;
import cn.itcast.bos.service.CourierService;
import org.mockito.internal.configuration.MockAnnotationProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import java.util.HashMap;
import java.util.Map;

/**
 * @author Jim-gongtiancheng
 * @create 2019 - 01 - 11 10:27
 */
@RestController
@RequestMapping("courier")
public class CourierController {
    @Autowired
    private CourierService courierService;
    //分页条件查询
    @RequestMapping("searchFindAll")
    public Map searchFindAll(int page, int rows, Courier courier){
        Pageable pageable = PageRequest.of(page,rows);
        Page<Courier> couriers = courierService.searchFindAll(pageable, courier);
        Map map = new HashMap();
        map.put("total",couriers.getTotalElements());
        map.put("rows",couriers.getContent());
        return map;
    }
}
