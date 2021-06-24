package com.cwj.common;

import com.cwj.common.service.UpFilm.UpFilm;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.ConfigurableApplicationContext;


@MapperScan(basePackages = "com.cwj.common.Dao")
@SpringBootApplication(scanBasePackages = "com.cwj.common")
public class CommonApplication {
    public static void main(String[] args) {
        ConfigurableApplicationContext run = SpringApplication.run(CommonApplication.class, args);

    }

}
