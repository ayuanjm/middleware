package com.yuan.middleware.ans;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.locks.LockSupport;

/**
 * 线程A打印1  线程B打印2  线程C打印3，要求三个线程按顺序执行
 *
 * @author yuanjm
 * @date 2020/8/14 4:07 下午
 */
public class Ans01 {
    static Thread a, b, c;

    public static void main(String[] args) {
        CountDownLatch countDownLatchB = new CountDownLatch(1);
        CountDownLatch countDownLatchC = new CountDownLatch(1);

        a = new Thread(() -> {
            System.out.println(Thread.currentThread().getName() + "1");
            countDownLatchB.countDown();
        });
        a.setName("a");
        b = new Thread(() -> {
            try {
                countDownLatchB.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName() + "2");
            countDownLatchC.countDown();
        });
        b.setName("b");
        c = new Thread(() -> {
            try {
                countDownLatchC.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName() + "3");

        });
        c.setName("c");

        c.start();
        b.start();
        a.start();
    }

    private static void two() {
        a = new Thread(() -> {
            System.out.println(Thread.currentThread().getName() + "1");
            LockSupport.unpark(b);
        });
        a.setName("a");
        b = new Thread(() -> {
            LockSupport.park();
            System.out.println(Thread.currentThread().getName() + "2");
            LockSupport.unpark(c);
        });
        b.setName("b");
        c = new Thread(() -> {
            LockSupport.park();
            System.out.println(Thread.currentThread().getName() + "3");

        });
        c.setName("c");

        c.start();
        b.start();
        a.start();
    }

    private static void one() {
        Thread a = new Thread(() -> {
            System.out.println("a-1");
        });
        Thread b = new Thread(() -> {
            try {
                a.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("b-2");
        });
        Thread c = new Thread(() -> {
            try {
                b.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("c-3");
        });

        c.start();
        b.start();
        a.start();
    }
}
