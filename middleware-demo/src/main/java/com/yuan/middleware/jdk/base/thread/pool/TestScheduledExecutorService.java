package com.yuan.middleware.jdk.base.thread.pool;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * 使用固定的频率执行某个任务
 *
 * @author yuanjm
 * @date 2020/8/25 6:47 下午
 */
public class TestScheduledExecutorService {
    public static void main(String[] args) {
        /**
         * 核心线程数：3
         * 最大线程数：Integer.MAX_VALUE  造成oom
         * 最大线程空闲时间：0
         * 最大线程空闲时间单位：NANOSECONDS 纳秒
         * 阻塞队列：new DelayedWorkQueue() 延迟队列
         */
        ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(3);
        /**
         *  使用固定的频率执行某个任务
         *  command: 执行的任务
         *  initialDelay: 第一次执行延时多久执行
         *  period: 每隔多久执行一次这个任务
         *  unit: 时间单位
         */
        scheduledExecutorService.scheduleAtFixedRate(() -> {
            System.out.println(System.currentTimeMillis() / 1000);
        }, 3, 5, TimeUnit.SECONDS);
    }
}
