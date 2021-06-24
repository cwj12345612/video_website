package com.cwj.common.controller;

import com.cwj.common.Utils.Utils;
import com.cwj.common.domain.Film;
import com.cwj.common.domain.MysqlFilm;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import redis.clients.jedis.Jedis;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URLDecoder;
import java.util.*;

/*
给请求页面发送json字符串
 */
@SuppressWarnings("all")
@Controller
public class MyController {
    @Autowired
    public Jedis jedis;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private HttpServletRequest request;
    @Autowired
    private HttpServletResponse response;
    @Autowired
    private ServletContext servletContext;
    @Autowired
    private RedisTemplate<Object,Object> redisTemplate;
    //-------------------------------------------------------------------------
    @Autowired
    private com.cwj.common.service.homepage.homepageInterface homepageInterface;
    @Autowired
    private com.cwj.common.service.UpFilm.UpFilm upFilm;
    @Autowired
    private com.cwj.common.service.explain.explainInterface explainInterface;
    @Autowired
    private com.cwj.common.service.category.categoryInterface categoryInterface;
    @Autowired
    private com.cwj.common.service.searchResult.searchResultInterface searchResultInterface;

    private String Commonurl;
    private String UserUrl;
    private String AdminUrl;
    //*************************************

//****************************************************************************

    /**
     * 获取服务器端Common模块的url地址
     * @return
     */
    //*******************************************************************
   // 获取模块的url地址
    @PostMapping({"/CommonUrl"})
    @ResponseBody
    private String GetCommonUrl() throws MalformedURLException {


        if(Commonurl==null) {

        String localAddr = request.getLocalAddr();
        int localPort = request.getLocalPort();
        String contextPath = request.getContextPath();
        Commonurl= "http://" + localAddr + ":" + localPort + "/" + contextPath;
//        System.out.println("你好出现url     "+url);
    //把url存在redis中

    }
        if(jedis.get("CommonUrl")==null) {
            jedis.set("CommonUrl", Commonurl);
        }
        return Commonurl;
    }
    @PostMapping("/UserUrl")
    @ResponseBody
    private String GetUserUrl(){
        if(UserUrl==null&&jedis.get("UserUrl")!=null) {
            UserUrl = jedis.get("UserUrl");
        }
        return UserUrl;
    }
    //****************************************************************
    @GetMapping("/")
    private String index(){

       return "redirect:/homepage.html";
    }
//*********************************************************************************************************
    /**
     * 调用service层获取特定数量的film
     * *  转换为json字符串发送给前端
     *
     * @param sum
     * @param
     */
    @PostMapping(value = "/homepage/FindFilmBySum/{sum}", produces="text/html;charset=UTF-8")
    @ResponseBody
    private String FindFilmBySum(@PathVariable(name = "sum") int sum) throws IOException {

        Object Map=redisTemplate.opsForHash().entries("FilmMap") ;
        Map<String,List<Film>> filmMap=(Map<String, List<Film>>)Map;

        //调用service层实现业务
        Map<String, List<Film>> filmMapBySum = homepageInterface.FilmMapBySum(sum,filmMap);

        //把结果转换为json字符串
        String json = objectMapper.writeValueAsString(filmMapBySum);

        //返回给前端json字符串

        return json;

    }

    /**
     * 填充home页面下方的影片列表   根据前端所需的影片类型及其对应的影片个数
     */
    @PostMapping(value = "/homepage/FillList", produces="text/html;charset=UTF-8")
    @ResponseBody
    private String FillListByArray(@RequestParam("video[]") String[] Types,@RequestParam("size[]") int[] sizes) throws IOException {

        Object map = redisTemplate.opsForHash().entries("FilmMap");

        Map filmMap = homepageInterface.FillListByArray(Types, sizes, (Map<String, List<Film>>) map);
        //转换为json字符串
        String json = objectMapper.writeValueAsString(filmMap);
//        System.out.println(json);
        return json;
    }

    //***************************************************************************************************************

    /**
     * 跳转到UpFilm页面时  查询 所有影片的没错 给前端返回一个json字符串  里面是影片名称的list集合
     *
     * @param request
     * @param response
     */
    @PostMapping(value = "/UpFilm/GetFilmTitleAll")
    @ResponseBody
    private String GetFilmTitleAll() throws IOException {
//        System.out.println("查询所有影片"+new Date(System.currentTimeMillis()).getTime());
        Object filmMap = redisTemplate.opsForHash().entries("FilmMap");
        List<Film> filmList = upFilm.GetFilmTitleAll((Map<String, List<Film>>) filmMap);

        List<String> FilmTitles = new ArrayList<>();
        for (Film film : filmList) {
            String title = film.getTitle();
            FilmTitles.add(title);
        }

        String json = objectMapper.writeValueAsString(FilmTitles);

        return json;
    }

    /**
     * 用户上传一部影片
     *
     * @param request
     * @param
     * @throws SQLException
     * @throws IOException
     */
    @PostMapping(value = "/UpFilm/UpFilm")
    private String UpFilm(MysqlFilm mysqlFilm,@RequestParam(name = "pict") MultipartFile pictFile, @RequestParam(name = "films") MultipartFile[] filmFiles) throws IOException {
//        System.out.println("文件上传。。。。。。。。。。。。。。。。。。。。。。。。。。。");
        boolean status = true;   //默认影片上传成功
        try {
             /*
             存在bug 待修复
                如果不是数据库本身的错误  而是其他错误  如存储文件错误
                   则数据表还会被添加上一行数据
              */
            System.out.println("类是什么： "+upFilm);
            upFilm.UpFilms(mysqlFilm,pictFile,filmFiles);

        } catch (Exception e) {
            status = false;
        }

        //重定向到UpFilmStatus页面

        String contextPath = request.getContextPath();

        return "redirect:/UpFilmStatus.html?="+status;
    }

    //***********************************************************************************************

    /*
     * 填充影片详情页信息
     * @param path
     * @param request
     * @param
     * @throws IOException
     */
    @RequestMapping(value = "/explain/FillFilmInformation")
    @ResponseBody
    private String FillFilmInformation(@RequestParam(name = "value") String path) throws IOException {

        //   web/pict/_870x518/权力的游戏.jpeg
        String decode = URLDecoder.decode(path, "utf-8");
        Commonurl=this.GetCommonUrl();
        int indexOf = decode.indexOf(Commonurl);
        if(indexOf!=-1){
            decode=decode.substring(Commonurl.length());
        }

        Object Map=redisTemplate.opsForHash().entries("FilmMap") ;


        Film film = explainInterface.GetFilmByPath(decode, (java.util.Map<String, List<Film>>) Map);

        if(film.getVideoPath().size()>10) {
            List<String> list = Utils.sortByListString(film.getVideoPath());
            film.setVideoPath(list);
        }
        // 转换为json字符串传输到前端
        String json = objectMapper.writeValueAsString(film);

        return json;

    }

    /**
     * 实现下载影片功能
     * @param FilmName
     * @param type
     * @param size
     * @param request
     * @param response
     * @throws IOException
     */
    @GetMapping(value = {"/explain/DownloadFilm/{FilmName}/{type}/{size}"})
    private void DownloadFilm(@PathVariable(name = "FilmName") String FilmName, @PathVariable(name = "type") String type,@PathVariable(name = "size") int size) throws IOException{

        //
        explainInterface.downloadFilm(FilmName,type,size,response);
    }
    //**********************************************************************

    /**
     * 填充分区列表 返回json字符串给前端
     * @return
     */
    @PostMapping(value = "/Category/fillOneByType")
    @ResponseBody
    private String FillListByTypeOne(@RequestParam(name = "type") String type,@RequestParam(name = "length") int length) throws JsonProcessingException {
//        System.out.println("查询影片");
        Object filmMap =redisTemplate.opsForHash().entries("FilmMap");

        List filmList = categoryInterface.GetFilmListByTypeAndLength((Map<String, List<Film>>) filmMap, type, length);

        //转为json字符串

        String json = objectMapper.writeValueAsString(filmList);

        return json;
    }

    /*
     * 分页查询
     * @param type
     * @param PageSize
     * @param FilmLength
     * @param request
     * @return
     */
    @PostMapping(value = "/Category/FillListByTypePageSize",produces="text/html;charset=UTF-8")
    @ResponseBody
    private String FillListByTypePageSize(@RequestParam(name = "type") String type,@RequestParam(name = "size") int PageSize,@RequestParam(name = "length") int FilmLength) throws JsonProcessingException {

        Object filmMap =redisTemplate.opsForHash().entries("FilmMap");

        List filmList = categoryInterface.GetFilmListByPageSizeAndType((Map<String, List<Film>>) filmMap, type, FilmLength, PageSize);

        //转换为json字符串
        String json = objectMapper.writeValueAsString(filmList);

        return json;
    }
    //********************************************************************************************************
    @PostMapping("/searchResult/FindFilmListByName")
    @ResponseBody
    private String FindFilmListByName(@RequestParam(name = "videoName") String videoName) throws JsonProcessingException {

        Object filmMap =redisTemplate.opsForHash().entries("FilmMap");

        List<Film> films = searchResultInterface.FindFilmListByName(videoName, (Map<String, List<Film>>) filmMap);
        //
        String json = objectMapper.writeValueAsString(films);

        return json;
    }
    //*******************************************************************
}