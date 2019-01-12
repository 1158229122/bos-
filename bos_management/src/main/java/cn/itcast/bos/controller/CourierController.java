package cn.itcast.bos.controller;

import cn.itcast.bos.domain.base.Courier;
import cn.itcast.bos.domain.base.Standard;
import cn.itcast.bos.domain.common.ResponseResult;
import cn.itcast.bos.service.CourierService;
import org.apache.commons.lang3.StringUtils;
import org.mockito.internal.configuration.MockAnnotationProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import javax.persistence.Id;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
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

        Pageable pageable = PageRequest.of(page-1,rows);
        Page<Courier> couriers = courierService.searchFindAll(pageable, courier);
        Map map = new HashMap();
        map.put("total",couriers.getTotalElements());
        List<Courier> content = couriers.getContent();
        map.put("rows",couriers.getContent());
        return map;
    }
    @RequestMapping("save")
    public ResponseResult save(Courier courier){
        try {
            if (courier.getId()==null){
                courier.setDeltag(Character.valueOf('1'));
            }
            courierService.saveCourier(courier);
            return  ResponseResult.SUCCESS();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseResult.FAIL();
        }
    }

    @RequestMapping("deleteByIds")
    public ResponseResult deleteById(Integer[] ids){
        try {
            courierService.deleteByIds(ids);
            return  ResponseResult.SUCCESS();
        } catch (Exception e) {
            e.printStackTrace();
            return  ResponseResult.FAIL();
        }
    }
    @RequestMapping("getStandards")
    public List getStandardAll(){
        List<Standard> standardList = courierService.getStandardAll();
        List list = new LinkedList();
        for (Standard standard : standardList) {
            Map map = new HashMap();
            map.put("id",standard.getId());
            map.put("text",standard.getName());
            list.add(map);
        }

        return list;
    }
    @RequestMapping("updateStatus")
    public ResponseResult updateStatus(Integer[] ids){
        try {
            courierService.updateStatus(ids);
            return  ResponseResult.SUCCESS();
        } catch (Exception e) {
            e.printStackTrace();
            return  ResponseResult.FAIL();
        }
    }
}
