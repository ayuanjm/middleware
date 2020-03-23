package com.yuan.middleware;

import com.yuan.middleware.springboot.SendMessage;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MiddlewareRabbitmqApplicationTests {
    @Autowired
    private ApplicationContext ioc;

    @Autowired
    private SendMessage sendMessage;

    @Test
    public void contextLoads() {
        sendMessage.sendDirectMessage();
    }

    @Test
    public void show(){
        System.out.println(ioc.containsBean("a"));
    }
}
