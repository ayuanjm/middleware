package com.yuan.middleware;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MiddlewareRedisApplicationTests {
    @Autowired
    private ApplicationContext ioc;
    @Autowired
    private StringRedisTemplate template;
    @Test
    public void contextLoads() {
        System.out.println(template.getDefaultSerializer());
    }

}
