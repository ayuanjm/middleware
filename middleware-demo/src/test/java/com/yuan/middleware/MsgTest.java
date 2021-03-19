package com.yuan.middleware;

import com.yuan.middleware.spring.dao.PlatformMessageMapper;
import com.yuan.middleware.spring.entity.PlatformMessage;
import com.yuan.middleware.spring.event.EvenPublish;
import com.yuan.middleware.spring.service.DeptService;
import com.yuan.middleware.spring.transaction.PlatformMessageService;
import javafx.print.PageLayout;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.UUID;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class MsgTest {
    @Autowired
    private ApplicationContext ioc;

    @Autowired
    private DeptService deptService;

    @Resource
    private PlatformMessageService platformMessageService;

    @Test
    public void contextLoads() {
        DeptService bean = ioc.getBean(DeptService.class);
        MDC.put("traceId", UUID.randomUUID().toString());
        log.info("a:{}", "aa");
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
    public void transactionTest() {
        PlatformMessage platformMessage = new PlatformMessage();
        platformMessage.setCode("a");
        platformMessageService.updateMessage(platformMessage);
    }
}
