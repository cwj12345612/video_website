package com.cwj.common.Dao;


import com.cwj.common.domain.MysqlFilm;

import java.util.List;

/**
 * 数据库的基本功能
 */
@SuppressWarnings("all")
public interface MysqlDao {
    /**
     * 向数据表中插入一条影片
     * @param mysqlFilm
     */
    void InsertFilm(MysqlFilm mysqlFilm);

    /**
     * 根据名称查询多条影片的信息
     * @param title
     * @return
     */
    List<MysqlFilm> findFilmsByTitle(String title);


    /**
     * 查询表中所有数据
     * @return
     */
     List<MysqlFilm> FindAll();

    /**
     * 查询影片类型个数
     * @return
     */
    List<String> FindType();
}
