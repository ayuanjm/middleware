package com.yuan.middleware.controller;

import com.yuan.middleware.service.RedissonDistributedLocker;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

/**
 * 分布式锁Redisson
 *
 * @author yuan
 * @date 2020/01/06
 */
@Slf4j
@RequestMapping("/redisson")
@RestController
public class RedissonDistributedLockerController {
    @Resource
    private RedissonDistributedLocker locker;

    @Resource
    private ThreadPoolTaskScheduler task_event;

    @GetMapping("/async")
    public void async() {
        log.info("redisson测试...");
        for (int i = 0; i < 5; i++) {
            task_event.execute(() -> {
                System.out.println("线程开始:" + Thread.currentThread().getName());
                boolean getLock = locker.tryLock("yuan", TimeUnit.SECONDS, 5L, 10L);
                if (getLock) {
                    System.out.println(Thread.currentThread().getName() + "获取到锁");
                    try {
                        //模拟业务场景执行时间
                        Thread.sleep(3000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } finally {
                        locker.unlock("yuan");
                        System.out.println(Thread.currentThread().getName() + "释放锁");
                    }
                }
            });
        }
    }

}