package com.yuan.middleware.base.thread.pool;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * 测试线程池 waitForTasksToCompleteOnShutdown 属性
 * 设置线程池关闭的时候等待所有任务都完成再继续销毁其他的Bean
 * scheduler.setWaitForTasksToCompleteOnShutdown(true);
 *
 * @author yuan
 * @date 2019/12/24
 */
@Slf4j
@Component
public class Task {
    @Resource
    private RedisTemplate<String, String> redisTemplate;

    @Async("executor_yuan")
    public void doTaskOne() throws Exception {
        log.info("开始做任务一");
        long start = System.currentTimeMillis();
        log.info(redisTemplate.boundValueOps("k").get());
        long end = System.currentTimeMillis();
        log.info("完成任务一，耗时：" + (end - start) + "毫秒");
    }

    @Async("executor_yuan")
    public void doTaskTwo() throws Exception {
        log.info("开始做任务二");
        long start = System.currentTimeMillis();
        log.info(redisTemplate.randomKey());
        long end = System.currentTimeMillis();
        log.info("完成任务二，耗时：" + (end - start) + "毫秒");
    }

    @Async("executor_yuan")
    public void doTaskThree() throws Exception {
        log.info("开始做任务三");
        long start = System.currentTimeMillis();
        log.info(redisTemplate.randomKey());
        long end = System.currentTimeMillis();
        log.info("完成任务三，耗时：" + (end - start) + "毫秒");
    }

}
