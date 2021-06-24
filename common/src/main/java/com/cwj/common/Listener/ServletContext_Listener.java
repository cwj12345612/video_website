package com.cwj.common.Listener;

import com.cwj.common.Utils.QueryFilmAll;
import com.cwj.common.domain.Film;
import io.netty.handler.codec.EncoderException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.servlet.ServletContext;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
@SuppressWarnings("all")
@Component
public class ServletContext_Listener implements ApplicationRunner  {

    @Autowired
    private QueryFilmAll queryFilmAll;
    @Autowired
    private ServletContext servletContext;

    @Autowired
    private RedisTemplate<Object,Object> redisTemplate;
    @Override
    public void run(ApplicationArguments args) throws Exception {
                /*
        监听项目启动
            加载所有影片信息进内存  存储为film的Map集合  存储在redis中
         */
        //获取ServletContext对象

        redisTemplate.delete("FilmMap");

        Map<String, List<Film>> FilmMap = null;

        try {
            FilmMap = queryFilmAll.GetFIlmAll(servletContext.getRealPath(""));

        } catch (IllegalAccessException | EncoderException |it.sauronsoftware.jave.EncoderException e) {
            e.printStackTrace();
        }


        //当字符串为空时 序列化就会失败 所以不能为空
        redisTemplate.opsForHash().putAll("FilmMap",FilmMap);
//*************************************************************************
//        Object Map = redisTemplate.opsForHash().entries("FilmMap");
//        Map<String,List<Film>> filmMap=(java.util.Map<String, List<Film>>) Map;
//
//        for (String type : filmMap.keySet()) {
//            List<Film> films = filmMap.get(type);
//            StringBuilder sb=new StringBuilder();
//            for (Film film : films) {
//                String title = film.getTitle();
//                sb.append("  "+title+"  ");
//            }
//            System.out.println(type+" : "+sb.toString());
//        }
//
    }


}
