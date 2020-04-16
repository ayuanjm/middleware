package com.yuan.middleware.algorithm.thread;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.locks.LockSupport;

/**
 * 线程A打印1  线程B打印2  线程C打印3，要求三个线程按顺序执行
 *
 * @author yjm
 * @date 2020/3/24 11:41 下午
 */
public class ThreadLoopOutput {
    static Thread a, b, c;

    public static void main(String[] args) {
        join();
//        countDownLatch();
//        lockSupport();
    }

    private static void lockSupport() {
        a = new Thread(() -> {
            System.out.print(1);
            LockSupport.unpark(b);
        });
        b = new Thread(() -> {
            LockSupport.park();
            System.out.print(2);
            LockSupport.unpark(c);
        });
        c = new Thread(() -> {
            LockSupport.park();
            System.out.print(3);
        });
        a.start();
        b.start();
        c.start();
    }

    private static void countDownLatch() {
        CountDownLatch downLatch1 = new CountDownLatch(1);
        CountDownLatch downLatch2 = new CountDownLatch(1);


        a = new Thread(() -> {
            downLatch1.countDown();
            System.out.print(1);
        });
        b = new Thread(() -> {
            try {
                downLatch1.await();
                System.out.print(2);
                downLatch2.countDown();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        c = new Thread(() -> {
            try {
                downLatch2.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.print(3);
        });
        a.start();
        b.start();
        c.start();
    }

    private static void join() {
        a = new Thread(() -> {
            System.out.print(1);
        });
        b = new Thread(() -> {
            try {
                a.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.print(2);
        });
        c = new Thread(() -> {
            try {
                b.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.print(3);
        });

        a.start();
        b.start();
        c.start();
    }
}
