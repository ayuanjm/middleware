package com.yuan.middleware;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class MiddlewareToolApplication {

    public static void main(String[] args) {
        SpringApplication.run(MiddlewareToolApplication.class, args);
    }

}
