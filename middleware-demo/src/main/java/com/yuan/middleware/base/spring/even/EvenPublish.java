package com.yuan.middleware.base.spring.even;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * 事件发布
 *
 * @author yuan
 * @date 2019/12/24
 */
@Component
public class EvenPublish {
    @Resource
    private ApplicationEventPublisher publisher;

    public void publish(String message) {
        EvenSource demo = new EvenSource(this, message);
        System.out.println(Thread.currentThread().getName() + ":publishEvent事件开始");
        publisher.publishEvent(demo);
        System.out.println(Thread.currentThread().getName() + ":publishEvent事件结束");
    }
}
