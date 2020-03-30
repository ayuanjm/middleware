package com.yuan.middleware;

import java.util.concurrent.TimeUnit;

/**
 * @author yjm
 * @date 2020/3/26 6:31 下午
 */
public class Demo {
    public static void main(String[] args) {
        Demo demo = new Demo();
        new Thread(()->{
            try {
                demo.m1();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();

        new Thread(()->{
            try {
                demo.m2();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }

    public synchronized void m1() throws InterruptedException {
        System.out.println(1);
        TimeUnit.SECONDS.sleep(10);
        System.out.println(1);
    }

    public synchronized void m2() throws InterruptedException {
        System.out.println(2);
        TimeUnit.SECONDS.sleep(1);
        System.out.println(2);
    }
}
