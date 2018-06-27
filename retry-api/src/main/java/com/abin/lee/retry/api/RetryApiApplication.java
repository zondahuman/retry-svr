package com.abin.lee.retry.api;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;

/**
 * Created by abin on 2018/6/27.
 */
@SpringBootApplication
public class RetryApiApplication extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        // 注意这里要指向原先用main方法执行的Application启动类
        return builder.sources(RetryApiApplication.class);
    }

    public static void main(String[] args) {
        SpringApplication.run(RetryApiApplication.class, args);
    }


}