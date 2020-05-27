package com.yuan.middleware;

import com.yuan.middleware.base.thread.pool.NamedThreadFactory;

import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author yjm
 * @date 2020/3/26 6:31 下午
 */
public class Demo {
    public static void main(String[] args) {
        ThreadPoolExecutor poolExecutor = new ThreadPoolExecutor(3, 5, 1,
                TimeUnit.SECONDS, new LinkedBlockingDeque<>(10),
                new NamedThreadFactory("test"), new ThreadPoolExecutor.AbortPolicy());
        poolExecutor.execute(() -> {
            System.out.println(Thread.currentThread().getThreadGroup());
            System.out.println(Thread.currentThread().getName());
        });
        poolExecutor.shutdown();
    }
}
