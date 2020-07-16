package com.yuan.middleware;

import com.yuan.middleware.separate.service.MarketActivityService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MiddlewareMysqlApplicationTests {
    @Autowired
    private ApplicationContext ioc;

    @Test
    public void contextLoads() {
        MarketActivityService bean = ioc.getBean(MarketActivityService.class);
        bean.selectActivity(1L);
    }

}
