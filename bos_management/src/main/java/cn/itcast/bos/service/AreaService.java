package cn.itcast.bos.service;

import cn.itcast.bos.domain.base.Area;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * @author Jim-gongtiancheng
 * @create 2019 - 01 - 12 14:39
 */
public interface AreaService {
    /**
     * 保存区域名
     * @param area
     */
    void saveArea(Area area);

    /**
     * 分页条件查询
     * @param pageable
     * @param area
     * @return
     */
    Page<Area> searchFindAll(Pageable pageable, Area area);

    /**
     * 根据id作废
     * @param ids
     */
    void deleteByIds(String[] ids);

    /**
     * 根据id修改状态
     * @param ids
     */
    void updateStatus(String[] ids);
}
