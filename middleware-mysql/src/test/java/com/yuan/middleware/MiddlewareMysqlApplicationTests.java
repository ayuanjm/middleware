package com.yuan.middleware;

import com.yuan.middleware.separate.service.MarketActivityService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MiddlewareMysqlApplicationTests {
    @Resource
    private MarketActivityService marketActivityService;

    /**
     * 动态修改注解属性
     *
     * @throws Exception
     */
    @Transactional(propagation = Propagation.REQUIRED)
    @Test
    public void contextLoads() throws Exception {
        Method method = MiddlewareMysqlApplicationTests.class.getMethod("contextLoads");
        Transactional annotation = method.getAnnotation(Transactional.class);
        InvocationHandler invocationHandler = Proxy.getInvocationHandler(annotation);
        Field value = invocationHandler.getClass().getDeclaredField("memberValues");
        value.setAccessible(true);
        Map<String, Object> memberValues = (Map<String, Object>) value.get(invocationHandler);
        Propagation propagation = (Propagation) memberValues.get("propagation");
        System.out.println("改变前：" + propagation);
        memberValues.put("propagation", Propagation.REQUIRES_NEW);
        System.out.println("改变后：" + annotation.propagation());
    }


    @Test
    public void selectActivity() {
        marketActivityService.selectActivity(1L);
    }

}
