package com.yuan.middleware.demo.service.impl;

import com.yuan.middleware.demo.service.TestAsync;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

/**
 * @author yuan
 */
@Slf4j
@Component
public class TestAsyncImpl implements TestAsync {

    @Override
    public void show() {
        try {
            Thread.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    @Async("task_event")
    public void info() {
        log.info("异步方法开始");
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        log.info("异步方法结束");
    }
}
