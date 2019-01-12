package cn.itcast.bos.controller;

import cn.itcast.bos.domain.base.Standard;
import cn.itcast.bos.domain.common.ResponseResult;
import cn.itcast.bos.service.StanderdService;
import cn.itcast.bos.util.CookieUtil;
import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.querydsl.QPageRequest;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

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

        return map;
    }
    @RequestMapping("deleteByIds")
    public ResponseResult deleteById(Integer[] ids){
        try {
            standerdService.deleteById(ids);
            return  ResponseResult.SUCCESS();
        } catch (Exception e) {
            e.printStackTrace();
            return  ResponseResult.FAIL();
        }
    }
    @RequestMapping("repeal")
    public ResponseResult repeal(HttpServletRequest request, HttpServletResponse response){
        try {
            String repeal = CookieUtil.getCookieValue(request, "repeal", "UTF-8");
            System.out.println(repeal);
            if (repeal==null||"".equals(repeal)||"[]".equals(repeal)||"[{}]".equals(repeal)){
                return new ResponseResult(false,"您还没有执行修改和删除操作哟!!");
            }
            List<Map> maps = JSON.parseArray(repeal, Map.class);
            int length = maps.size();
            //获取集合中最后一个数据
            Map map = maps.get(length - 1);

            for (Object o : map.keySet()) {
                String key = (String) o;

                if ("update-standerd".equals(key)){
                    //是更新
                    Object value =  map.get(key);
                    String s = JSON.toJSONString(value);
                    Standard standard = JSON.parseObject(s, Standard.class);
                    System.out.println(key+"===="+standard);
                    //执行跟新还原
                    standerdService.save(standard);
                }
                if ("delete-standerd".equals(key)){
                    //是删除
                    String idStr = JSON.toJSONString(map.get(key));
                    List<Integer> values = JSON.parseArray(idStr, Integer.class);
                    System.out.println(key+"===="+values);
                    for (Integer value : values) {
                        Standard standard = standerdService.findById(value);
                        standard.setIsDelete("1");//启用
                        standerdService.save(standard);
                    }
                }
            }
            maps.remove(length-1);
            String result = JSON.toJSONString(maps);
            //响应给浏览器
            CookieUtil.setCookie(request,response,"repeal",result,60*30,"UTF-8");
            return  ResponseResult.SUCCESS();
        } catch (Exception e) {
            e.printStackTrace();
            return  ResponseResult.FAIL();
        }
    }
}
