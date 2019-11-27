package com.yuan.middleware;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;

/**
 * ImportResource: 导入Spring的配置文件，让配置文件里面的内容生效;
 * Spring Boot里面没有Spring的配置文件，我们自己编写的配置文件，也不能自动识别
 *
 * @author yuan
 * @date 2019/11/26
 */
@ImportResource(locations = "classpath:bean.xml")
@SpringBootApplication
public class MiddlewareDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(MiddlewareDemoApplication.class, args);
    }

}
