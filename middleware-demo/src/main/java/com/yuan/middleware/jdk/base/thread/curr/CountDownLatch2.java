package com.yuan.middleware.jdk.base.thread.curr;

import java.util.concurrent.CountDownLatch;

/**
 * @author yjm
 * @date 2020/3/24 10:18 下午
 */
public class CountDownLatch2 {
    static Thread a, b, c;

    public static void main(String[] args) {
        a();

    }

    private static void a() {
        CountDownLatch downLatch1 = new CountDownLatch(1);
        CountDownLatch downLatch2 = new CountDownLatch(1);


        a = new Thread(() -> {
            downLatch1.countDown();
            System.out.println("a");
        });
        b = new Thread(() -> {
            try {
                downLatch1.await();
                System.out.println("b");
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
            System.out.println("c");
        });
        a.start();
        b.start();
        c.start();
    }

}
