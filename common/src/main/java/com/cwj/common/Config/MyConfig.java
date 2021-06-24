package com.cwj.common.Config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.Jedis;

@Configuration
public class MyConfig {
    @Bean("jedis")
    public Jedis getJedis(){
        return new Jedis();
    }
}
