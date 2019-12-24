
package com.yuan.middleware.spring.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

import java.util.concurrent.*;

/**
 * @author yuan
 * @date 2019/12/24
 */
@EnableAsync
@Configuration
public class ThreadPoolConfig {
    @Bean("executor_yuan")
    public Executor taskExecutor() {
        // 创建一个线程池对象
        ExecutorService threadPool = new ThreadPoolExecutor(
                // 核心线程大小
                30,
                // 最大线程
                50,
                // 空闲等待销毁时间2分钟
                2L,
                TimeUnit.SECONDS,
                // 保存等待执行的任务,基于数组结构的有界阻塞队列长度为 10
                new ArrayBlockingQueue<>(3),
                // 设置创建线程的工厂
                Executors.defaultThreadFactory(),
                // 线程池的拒绝策略 用调用者所在的线程来执行任务
                new ThreadPoolExecutor.CallerRunsPolicy()
        );
        // 创建一个线程池对象
        ThreadPoolTaskScheduler scheduler = new ThreadPoolTaskScheduler();
        // 定义一个线程池大小
        scheduler.setPoolSize(200);
        // 线程池名的前缀
        scheduler.setThreadNamePrefix("executor_yuan_");
        // 设置线程池关闭的时候等待所有任务都完成再继续销毁其他的Bean
        scheduler.setWaitForTasksToCompleteOnShutdown(true);
        // 设置线程池中任务的等待时间，如果超过这个时候还没有销毁就强制销毁，以确保应用最后能够被关闭，而不是阻塞住
        scheduler.setAwaitTerminationSeconds(60);
        // 线程池对拒绝任务的处理策略,当线程池没有处理能力的时候，该策略会直接在 execute 方法的调用线程中运行被拒绝的任务；如果执行程序已关闭，则会丢弃该任务
        scheduler.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        return threadPool;
    }
}
