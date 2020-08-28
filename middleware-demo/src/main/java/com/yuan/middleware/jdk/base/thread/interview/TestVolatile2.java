package com.yuan.middleware.jdk.base.thread.interview;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author yuanjm
 * @date 2020/7/21 2:43 下午
 */
public class TestVolatile2 {
    /**
     * 可以看出volatile只保证可见性，不保证原子性，当是全局共享变量时不能只用volatile，保证线程安全。
     */
    public static volatile int count = 0;
    public static Integer index = 10000;
    public Integer dummy = 10000;
    public static AtomicInteger atomicInteger = new AtomicInteger(0);
    /**
     * 可以使用threadLocal解决全局变量的线程安全问题
     */
    public static final ThreadLocal<Integer> money = new ThreadLocal();

    public static void main(String[] args) throws InterruptedException {
        TestVolatile2 testVolatile2 = new TestVolatile2();
        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                money.set(testVolatile2.dummy);
                Integer integer = money.get();
                for (int j = 0; j < 10000; j++) {
                    integer--;
                }
                System.out.println(integer);
            }).start();
        }
    }

    private static void testStatic() {
        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                money.set(index);
                Integer integer = money.get();
                for (int j = 0; j < 10000; j++) {
                    integer--;
                }
                System.out.println(integer);
            }).start();
        }
    }

    private static void sync() throws InterruptedException {
        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                for (int j = 0; j < 10000; j++) {
                    count++;
                    atomicInteger.getAndIncrement();
                }
            }).start();
        }
        TimeUnit.SECONDS.sleep(3);
        System.out.println(count);
        System.out.println(atomicInteger);
    }

}
