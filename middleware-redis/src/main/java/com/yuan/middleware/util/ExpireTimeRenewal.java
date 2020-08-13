package com.yuan.middleware.util;

import java.util.concurrent.TimeUnit;

/**
 * @author yuanjm
 * @date 2020/8/13 9:55 上午
 */
public class ExpireTimeRenewal {
    public static volatile boolean flag = true;
    public static ThreadLocal<Boolean> threadLocal = new InheritableThreadLocal() {
        @Override
        protected Object initialValue() {
            return true;
        }
    };

    public static void main(String[] args) throws InterruptedException {
        System.out.println(threadLocal.get());
        Thread a = new Thread(() -> {
            while (flag) {
                try {
                    TimeUnit.SECONDS.sleep(1);
                    System.out.println("尝试续约。。。");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        a.start();
        TimeUnit.SECONDS.sleep(5);
        flag = false;
    }

}
