package cn.itcast.bos.controller;

import cn.itcast.bos.service.CourierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Jim-gongtiancheng
 * @create 2019 - 01 - 11 10:27
 */
@RestController
@RequestMapping("courier")
public class CourierController {
    @Autowired
    private CourierService courierService;
}
