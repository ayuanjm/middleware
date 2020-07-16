package com.yuan.middleware;

import com.yuan.middleware.jdk.base.spring.even.EvenPublish;
import com.yuan.middleware.spring.service.DeptService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.junit4.SpringRunner;


@RunWith(SpringRunner.class)
@SpringBootTest
public class MiddlewareDemoApplicationTests {
    @Autowired
    private ApplicationContext ioc;

    @Autowired
    private DeptService deptService;


    @Test
    public void contextLoads() {
        DeptService bean = ioc.getBean(DeptService.class);
        System.out.println(bean);
    }

    /**
     * 测试spring even
     */
    @Test
    public void even() {
        EvenPublish evenPublish = (EvenPublish) ioc.getBean("evenPublish");
        evenPublish.publish("yuan");
    }

    @Test
    public void Advice() {
        deptService.show("yuan");
    }


}
