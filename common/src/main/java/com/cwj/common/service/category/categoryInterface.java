package com.cwj.common.service.category;



import com.cwj.common.domain.Film;

import java.util.List;
import java.util.Map;

/**
 * 影片分区的所有业务功能
 */
public interface categoryInterface {
    /**
     * 根据影片类型 获取一定数量的影片
     * @return
     */
    List GetFilmListByTypeAndLength(Map<String,List<Film>> filmMap, String type, int length);

    /**
     * 分页查询影片
     * @param filmMap
     * @param type
     * @param length
     * @param pageSize
     * @return
     */
    List GetFilmListByPageSizeAndType(Map<String,List<Film>> filmMap,String type,int length,int pageSize);
}
