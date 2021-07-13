package com.yuan.middleware;

import com.yuan.middleware.service.RedissonDistributedLocker;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class RedisTest {
    @Autowired
    private ApplicationContext ioc;
    @Autowired
    private StringRedisTemplate redisTemplate;

    @Autowired
    private RedissonDistributedLocker locker;

    @Autowired
    private ThreadPoolTaskScheduler taskScheduler;

    @Resource
    private RedissonClient redissonClient;

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
                taskScheduler.execute(() -> async());
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

    @Test
    public void testTryLock() throws InterruptedException {
        for (int i = 0; i < 2; ++i) {
            taskScheduler.execute(this::lock);
        }
        TimeUnit.SECONDS.sleep(30);
    }

    private void tyrLock() {
        RLock lock = redissonClient.getLock("2021");
        if (lock.tryLock()) {
            try {
                log.info("获得到锁");
                TimeUnit.SECONDS.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
                log.info("释放锁");
            }
        } else {
            log.info("获取锁失败");
        }
    }

    private void lock() {
        RLock lock = redissonClient.getLock("2021");
        lock.lock();
        try {
            log.info("获得到锁");
            TimeUnit.SECONDS.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
            log.info("释放锁");
        }
    }
}
