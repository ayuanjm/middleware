package com.yuan.middleware;

import com.yuan.middleware.service.RedissonDistributedLocker;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MiddlewareRedisApplicationTests {
    @Autowired
    private ApplicationContext ioc;
    @Autowired
    private StringRedisTemplate redisTemplate;

    @Autowired
    private RedissonDistributedLocker locker;

    @Autowired
    private ThreadPoolTaskScheduler task_event;

    @Test
    public void contextLoads() {
        Object redisProperties = ioc.getBean("redisProperties");
        System.out.println(redisProperties);
    }

    @Test
    public void redis() {
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(// 核心线程大小
                30,
                // 最大线程
                50,
                // 空闲等待销毁时间2分钟
                2L,
                TimeUnit.SECONDS,
                // 保存等待执行的任务,基于数组结构的有界阻塞队列长度为 3
                new ArrayBlockingQueue<>(3),
                // 设置创建线程的工厂
                Executors.defaultThreadFactory(),
                // 线程池的拒绝策略
                new ThreadPoolExecutor.CallerRunsPolicy());
        try {
            for (int i = 0; i < 2; i++) {
                task_event.execute(() -> async());
            }
        } finally {
            threadPoolExecutor.shutdown();
        }
    }

    @Test
    public void test() {
        async();
    }

    private void async() {
        System.out.println("线程开始:" + Thread.currentThread().getName());
        boolean tryLock = locker.tryLock("yuan", TimeUnit.SECONDS, 5L, 10L);
        if (tryLock) {
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
    }
}
