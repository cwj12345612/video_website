package com.cwj.common.service.explain;


import com.cwj.common.domain.Film;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * 此接口定义了 explain页面的全部功能
 */
public interface explainInterface {
    /**
     * 根据前端传回的pictPath路径 找到对应的film对象 并返回给Controller
     * @return
     */
    public Film GetFilmByPath(String pictPath,Map<String,List<Film>> FilmMap);

    /**
     * 根据前端传回的影片路径数组  发送对应影片给前端
     * @param response
     */
    public void downloadFilm(String filmName, String type, int size, HttpServletResponse response) throws IOException;
}
