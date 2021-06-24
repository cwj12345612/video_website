package com.cwj.common.service.searchResult;

import com.cwj.common.domain.Film;

import java.util.List;
import java.util.Map;

/*
搜索结果展示页的全部功能
 */
@SuppressWarnings("all")
public interface searchResultInterface {
    public List<Film> FindFilmListByName(String Name, Map<String,List<Film>> filmMap);
}
