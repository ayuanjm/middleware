package com.yuan.middleware.jdk.base.thread.pool;

import cn.hutool.core.thread.NamedThreadFactory;

import java.time.LocalTime;
import java.util.Random;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Copyright(c) 2018 Sunyur.com, All Rights Reserved.
 * Project: message
 *
 * @author: yuanjiamin
 * CreateDate: 2020/12/30 10:48 上午
 * Description: 线程池隔离: 一个线程的异常不影响其他线程
 */
public class ThreadQuarantine {
    public static void main(String[] args) throws InterruptedException {
        ThreadPoolExecutor one = new ThreadPoolExecutor(
                3,
                5,
                60,
                TimeUnit.SECONDS,
                new LinkedBlockingQueue<>(5),
                new NamedThreadFactory("one", false),
                new ThreadPoolExecutor.AbortPolicy());
        ThreadPoolExecutor two = new ThreadPoolExecutor(
                10,
                15,
                60,
                TimeUnit.SECONDS,
                new LinkedBlockingQueue<>(100),
                new NamedThreadFactory("two", false),
                new ThreadPoolExecutor.AbortPolicy());
        Random random = new Random(3);
        AtomicInteger oneCount = new AtomicInteger(0);
        AtomicInteger twoCount = new AtomicInteger(0);

        for (int i = 0; i < 100; i++) {
            TimeUnit.NANOSECONDS.sleep(1);
            one.execute(() -> {
                two.execute(() -> {
                    try {
                        TimeUnit.NANOSECONDS.sleep(random.nextInt() * 100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println(Thread.currentThread().getName() + ": " + twoCount.incrementAndGet());
                });
                System.out.println(Thread.currentThread().getName() + ": " + oneCount.incrementAndGet());
            });
        }
    }

    /**
     * 线程异常测试，一个线程的异常不影响其他线程
     *
     * @param one
     * @param two
     * @throws InterruptedException
     */
    private static void extracted(ThreadPoolExecutor one, ThreadPoolExecutor two) throws InterruptedException {
        one.execute(() -> {
            System.out.println(Thread.currentThread().getName() + ": " + LocalTime.now());
            two.execute(() -> {
                try {
                    System.out.println(Thread.currentThread().getName() + ": " + LocalTime.now());
                    TimeUnit.SECONDS.sleep(3);
                    System.out.println(Thread.currentThread().getName() + ": " + LocalTime.now());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                //抛出异常
                int a = 1 / 0;
            });
            try {
                TimeUnit.SECONDS.sleep(5);
                System.out.println(Thread.currentThread().getName() + ": " + LocalTime.now());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        System.out.println(Thread.currentThread().getName() + ": " + LocalTime.now());
        TimeUnit.SECONDS.sleep(15);
        System.out.println(Thread.currentThread().getName() + ": " + LocalTime.now());
    }
}
