package com.cwj.common.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 此类与数据库里的film表对应
 */
@Data
@NoArgsConstructor
public class MysqlFilm implements Serializable {
    private int id;   //在数据表中的序号
    private String title = null;  //影片名称
    private String protagonist = null; //主演
    private String director = null;//导演
    private String type = null; //类型
    private String releaseDate = null; //上映时间
    private String synopsis = null;         //剧情简介
    private String location = null;      //影片地区
    private String filmSource = null;       //影片来源

}