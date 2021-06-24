package com.cwj.user.Controller;

import com.cwj.user.dao.userDao;
import com.cwj.user.domain.user;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import redis.clients.jedis.Jedis;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * user模块不能直接访问页面 必须经过controller
 */
@SuppressWarnings("all")
@Controller
public class MyController {

    @Autowired
    private Jedis jedis;
    @Autowired
    private RedisTemplate<Object,Object> redisTemplate;
    @Autowired
    private HttpServletRequest request;
    @Autowired
    private HttpServletResponse response;
    @Autowired
    private userDao userDao;
    @Autowired
    private ServletContext servletContext;
    @Autowired
    private ObjectMapper objectMapper;
//**************************************************************
    private String UserUrl;
    private String CommonUrl;
    @RequestMapping("/UserUrl")
    @ResponseBody
    private String GetUserUrl(){

        if(UserUrl==null) {

            String localAddr = request.getLocalAddr();
            int localPort = request.getLocalPort();
            String contextPath = request.getContextPath();
            UserUrl= "http://" + localAddr + ":" + localPort + "/" + contextPath;
//        System.out.println("你好出现url     "+url);
            //把url存在redis中

        }
        if(jedis.get("UserUrl")==null) {
            jedis.set("UserUrl", UserUrl);
        }

       return UserUrl;
    }
    @PostMapping("/CommonUrl")
    @ResponseBody
    private String getCommonUrl(){
        System.out.println("请求commonurl");
            CommonUrl=jedis.get("CommonUrl");

        return CommonUrl;
    }
    //*************************************************************
    @RequestMapping("/login")
    private String login(user u) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        System.out.println(u);
        List<user> userList = (List<com.cwj.user.domain.user>) servletContext.getAttribute("userList");
        System.out.println(userList);
        int indexOf = userList.indexOf(u);
        if(indexOf!=-1){
            if(request.getSession().getAttribute("user")==null) {
                //存在session中
                request.getSession().setAttribute("user" ,userList.get(indexOf));
            }

            Map<String, String> userMap = BeanUtils.describe(userList.get(indexOf));
            jedis.hmset("user"+u.getUsername()+u.getPassword(),userMap);
            return "main";
        }else {
            return "Status";
        }
    }

    /**
     * 获取会话中存储的user
     * @return
     * @throws JsonProcessingException
     */
    @PostMapping("/GetUser")
    @ResponseBody
    private String GetUser() throws JsonProcessingException {
        user user = (com.cwj.user.domain.user) request.getSession().getAttribute("user");
        if(user!=null){
            return objectMapper.writeValueAsString(user);
        }else {
            return null;
        }

    }

}
