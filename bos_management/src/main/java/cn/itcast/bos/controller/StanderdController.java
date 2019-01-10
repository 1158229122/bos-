package cn.itcast.bos.controller;

import cn.itcast.bos.domain.base.Standard;
import cn.itcast.bos.domain.common.ResponseResult;
import cn.itcast.bos.service.StanderdService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.querydsl.QPageRequest;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import java.util.HashMap;
import java.util.Map;
/**
 * @author Jim-gongtiancheng
 * @create 2019 - 01 - 09 20:12
 */
@RestController
@RequestMapping("/standerd")
public class StanderdController {
    @Autowired
    private StanderdService standerdService;
    @RequestMapping("/save")
    public ResponseResult save(Standard standard){
        try {
            standerdService.save(standard);
            return  ResponseResult.SUCCESS();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseResult.FAIL();
        }
    }
    @RequestMapping("/findAll")
    public Map findAll(int page,int rows){
        if (page<=0){
            page=1;
        }
        if (rows<=0||rows>=50){
            rows = 5;
        }
        Pageable pageable = PageRequest.of(page-1,rows);
        Page<Standard> standards = standerdService.findAll(pageable);
        Map map = new HashMap();
        map.put("total",standards.getTotalElements());
        map.put("rows",standards.getContent());
        for (Standard standard : standards.getContent()) {
            System.out.println(standard);
        }
        return map;
    }
    @RequestMapping("deleteById")
    public ResponseResult deleteById(Integer id){
        try {
            standerdService.deleteById(id);
            return  ResponseResult.SUCCESS();
        } catch (Exception e) {
            e.printStackTrace();
            return  ResponseResult.FAIL();
        }
    }
}
