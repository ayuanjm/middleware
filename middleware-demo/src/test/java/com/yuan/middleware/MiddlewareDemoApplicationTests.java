package com.yuan.middleware;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;


@RunWith(SpringRunner.class)
@SpringBootTest
public class MiddlewareDemoApplicationTests {
    @Autowired
    private ApplicationContext ioc;

    @Test
    public void contextLoads() {
        System.out.println(ioc.getBean("beanScope"));
    }

    public static void main(String[] args) {

    }

}
