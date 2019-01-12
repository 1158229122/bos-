package cn.itcast.bos.service;

import cn.itcast.bos.domain.base.Courier;
import cn.itcast.bos.domain.base.Standard;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * @author Jim-gongtiancheng
 * @create 2019 - 01 - 11 10:29
 */
public interface CourierService {
    /**
     * 分页条件查询
     * @param pageable
     * @param courier
     * @return
     */
    Page<Courier> searchFindAll(Pageable pageable, Courier courier);

    /**
     * 根据id删除
     * @param courier
     */
    void saveCourier(Courier courier);
    /**
     * 根据ids删除
     * @param ids
     */
    void deleteByIds(Integer[] ids);

    /**
     * 查询所有的授牌标准的规格
     * @return
     */
    List<Standard> getStandardAll();

    /**
     * 更新激活状态
     * @param ids
     */
    void updateStatus(Integer[] ids);
}
