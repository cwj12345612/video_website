package com.cwj.common.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/*
影片类
一部影片的属性和方法
 */
/*
影片名称
主演
导演
类型
上映时间
剧情简介
影片大小
影片地区
单集影片时长
影片来源
一张570x516图片的服务器相对路径
一张270x244图片的服务器相对路径
一张870x518图片的服务器相对路径
影片在服务器的相对路径
集数
 */
@Data
@NoArgsConstructor
public class Film implements Serializable {

    private String title = null;  //影片名称
    private String protagonist = null; //主演
    private String director = null;//导演
    private String type = null; //类型
    private String releaseDate = null; //上映时间
    private String synopsis = null;         //剧情简介
    private int size = -1;                //影片大小 单位为MB
    private int length = -1;             //单集影片时长   单位为min
    private String location = null;      //影片地区
    private String filmSource = null;       //影片来源
    private String _570x516pict = null;  //一张570x516图片的服务器相对路径
    private String _270x244pict = null;  //一张270x244图片的服务器相对路径
    private String _870x518pict = null;  //一张870x518图片的服务器相对路径
    private List<String> VideoPath = null; //影片在服务器的相对路径
    private int Episodes = -1;  //集数

 }