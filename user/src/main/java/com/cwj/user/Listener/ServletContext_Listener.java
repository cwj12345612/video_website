package com.cwj.user.Listener;

import com.cwj.user.dao.userDao;
import com.cwj.user.domain.user;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;

import javax.servlet.ServletContext;
import java.util.Arrays;
import java.util.List;

@SuppressWarnings("all")
@Component
public class ServletContext_Listener implements ApplicationRunner {

    @Autowired
    RedisTemplate<Object,Object> redisTemplate;
    @Autowired
    private Jedis jedis;
    @Autowired
    private userDao userDao;
    @Autowired
    private ServletContext servletContext;
    @Override
    public void run(ApplicationArguments args) throws Exception {
        jedis.set("UserUrl","http://172.28.92.139:8080/");
        List<user> userList = userDao.findAll();

        //把user集合存在ServletContext中
        servletContext.setAttribute("userList",userList);
    }
}
