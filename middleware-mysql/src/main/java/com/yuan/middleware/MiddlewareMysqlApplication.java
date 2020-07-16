package com.yuan.middleware;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

/**
 * @author yuan
 */
@MapperScan(basePackages = "com.yuan.middleware.separate.dao")
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
public class MiddlewareMysqlApplication {

    public static void main(String[] args) {
        SpringApplication.run(MiddlewareMysqlApplication.class, args);
    }

}
