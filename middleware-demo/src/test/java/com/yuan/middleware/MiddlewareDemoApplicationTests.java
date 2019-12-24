package com.yuan.middleware;

import com.yuan.middleware.base.spring.even.EvenPublish;
import com.yuan.middleware.base.thread.Task;
import lombok.SneakyThrows;
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

    @Autowired
    private Task task;

    @Test
    public void contextLoads() {
        System.out.println(ioc.getBean("beanScope"));
    }

    /**
     * 测试spring even
     */
    @Test
    public void even() {
        EvenPublish evenPublish = (EvenPublish) ioc.getBean("evenPublish");
        evenPublish.publish("yuan");
    }

    /**
     * 测试线程池 waitForTasksToCompleteOnShutdown 属性
     */
    @Test
    @SneakyThrows
    public void test() {

//        for (int i = 0; i < 100; i++) {
//            task.doTaskOne();
//            task.doTaskTwo();
//            task.doTaskThree();
//
//            if (i == 99) {
//                System.exit(0);
//            }
//        }
        task.doTaskOne();
        System.exit(0);
    }

    public static void main(String[] args) {

    }

}
