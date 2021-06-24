package com.cwj.common.service.homepage;


import com.cwj.common.domain.Film;

import java.util.List;
import java.util.Map;

/**
 * 此类是homepage所要执行的全部业务
 */
@SuppressWarnings("all")
public interface homepageInterface {
    /**
     * 从ServletContext中获取全部影片 并查找sum数量的影片返回给控制器  以map集合形式返回
     * @param sum
     * @param FilmMap
     * @return
     */
    public Map FilmMapBySum(int sum, Map<String, List<Film>> FilmMap);

    /**
     * 根据前端所需影片的类型及其对应的数量  查询film信息  返回一个map集合
     * @param Types
     * @param nums
     * @return
     */
    public Map FillListByArray(String[] Types,int[] nums,Map<String,List<Film>> filmMap);
}
