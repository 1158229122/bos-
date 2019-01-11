package cn.itcast.bos.service;

import cn.itcast.bos.domain.base.Standard;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


/**
 * @author Jim-gongtiancheng
 * @create 2019 - 01 - 09 20:12
 */
public interface StanderdService {
    /**
     * 执行保存
     * @param standard
     */
    void save(Standard standard);

    /**
     * 分页查询
     * @param pageable
     * @return
     */
    Page<Standard> findAll(Pageable pageable);

    /**
     * 根据id删除
     * @param id
     */
    void deleteById(Integer id);

    /**
     * 根据id查询
     * @param value
     * @return
     */
    Standard findById(Integer value);
}
