package com.cwj.common.service.UpFilm;



import com.cwj.common.domain.Film;
import com.cwj.common.domain.MysqlFilm;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

public interface UpFilm {
    /**
     * 前端上传数据一部影片到服务器服务器存储此影片
     * @param
     */
    public void UpFilms(MysqlFilm mysqlFilm, MultipartFile pictFile,MultipartFile[] filmFiles) throws Exception;

    /**
     * 查询所有film影片 返回一个list集合  元素是影片
     * @return
     */
   public List<Film> GetFilmTitleAll(Map<String,List<Film>> FilmMap);
}
