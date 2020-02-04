package com.yuan.middleware.base.thread.curr;

/**
 * 内存可见性
 *
 * @author yuan
 * @date 2020/01/13
 */
public class ThreadVisibility {
    private static volatile boolean flag = true;

    public static void main(String[] args) throws InterruptedException {
        new Thread(() -> {
            while (flag) {
                //do sth
            }
        }, "server").start();


        Thread.sleep(1000);

        flag = false;
    }
}
