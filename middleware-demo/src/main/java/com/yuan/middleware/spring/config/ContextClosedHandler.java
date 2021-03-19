package com.yuan.middleware.spring.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * 线程池等待优雅停机:实现在bean销毁之前关闭线程池
 * ApplicationListener<ContextClosedEvent> 来监听spring上下文关闭事件，该事件发生在上下文和spring bean销毁之前
 *
 * @author yuan
 * @date 2019/12/24
 */
@Slf4j
@Component
public class ContextClosedHandler implements ApplicationListener<ContextClosedEvent> {
    private static final int WAIT_TIME = 10;

    @Resource
    private ThreadPoolTaskScheduler scheduler;

    @Resource
    private RedisTemplate<String, String> redisTemplate;


    @Override
    public void onApplicationEvent(ContextClosedEvent contextClosedEvent) {
        System.out.println("【ContextClosedHandler】 start:" + contextClosedEvent.getSource());
        scheduler.execute(() -> {
            try {
                Thread.sleep(50000);
                System.out.println("【ContextClosedHandler】sleep end");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
//            System.out.println("redis value:" + redisTemplate.boundValueOps("k").increment());
        });
        System.out.println("【ContextClosedHandler】 end ----------");
        shutdownAndAwaitTermination(scheduler.getScheduledThreadPoolExecutor());
    }

    private void shutdownAndAwaitTermination(ExecutorService pool) {
        pool.shutdown(); // Disable new tasks from being submitted
        try {
            // Wait a while for existing tasks to terminate
            if (!pool.awaitTermination(WAIT_TIME, TimeUnit.SECONDS)) {
                pool.shutdownNow(); // Cancel currently executing tasks
                // Wait a while for tasks to respond to being cancelled
                if (!pool.awaitTermination(WAIT_TIME, TimeUnit.SECONDS)) {
                    System.err.println("Pool did not terminate");
                }
            }
        } catch (InterruptedException ie) {
            // (Re-)Cancel if current thread also interrupted
            pool.shutdownNow();
            // Preserve interrupt status
            Thread.currentThread().interrupt();
        }
    }
}
