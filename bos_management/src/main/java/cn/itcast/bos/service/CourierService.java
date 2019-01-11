package cn.itcast.bos.service;

import cn.itcast.bos.domain.base.Courier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * @author Jim-gongtiancheng
 * @create 2019 - 01 - 11 10:29
 */
public interface CourierService {
    Page<Courier> searchFindAll(Pageable pageable, Courier courier);
}
