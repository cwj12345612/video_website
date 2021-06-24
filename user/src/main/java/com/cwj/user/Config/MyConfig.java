package com.cwj.user.Config;

import redis.clients.jedis.Jedis;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MyConfig {
    @Bean("jedis")
    public Jedis getJedis(){
        return new Jedis();
    }
}
