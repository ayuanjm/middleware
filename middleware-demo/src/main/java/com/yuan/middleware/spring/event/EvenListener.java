package com.yuan.middleware.spring.event;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * 事件监听
 * EnableAsync 相当于 xml中的
 * <!-- 任务执行器 -->
 * <task:executor id="executor_yuan"/>
 * <!--开启注解调度支持 @Async -->
 * <task:annotation-driven executor="executor_yuan"/>
 * 如果proxy-target-class 属性值被设置为true，那么基于实现类的代理将起作用（这时需要cglib库）。
 * 如果proxy-target-class属值被设置为false或者这个属性被省略，那么标准的JDK 基于接口的代理将起作用。即使你未声明 proxy-target-class="true" ，但运行类没有继承接口，
 * spring也会自动使用CGLIB代理（高版本spring自动根据运行类选择 JDK 或 CGLIB 代理）
 *
 * @author yuan
 * @date 2019/12/24
 */
@Component
@Slf4j
public class EvenListener implements ApplicationListener<EvenSource> {
    @SneakyThrows
    @Async("executor_yuan")
    @Override
    public void onApplicationEvent(EvenSource evenSource) {
        System.out.println(Thread.currentThread().getName() + ":EvenListener监听消息" + evenSource.getMessage());
        TimeUnit.MINUTES.sleep(1);
        System.out.println("listener end...");
    }
}
