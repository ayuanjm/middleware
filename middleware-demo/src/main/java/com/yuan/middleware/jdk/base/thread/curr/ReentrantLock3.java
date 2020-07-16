package com.yuan.middleware.jdk.base.thread.curr;

import java.util.concurrent.locks.ReentrantLock;

/**
 * ReentrantLock 公平与不公平
 *
 * @author yuan
 * @date 2020/02/08
 */
public class ReentrantLock3 implements Runnable {
    /**
     * 默认为非公平锁，就是不用排队，可以随机分配
     */
    private static ReentrantLock lock = new ReentrantLock();

    @Override
    public void run() {
        for (int i = 0; i < 100; i++) {
            try {
                lock.lock();
                System.out.println(Thread.currentThread().getName() + "获得锁");
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        }
    }

    public static void main(String[] args) {
        ReentrantLock3 reentrantLock3 = new ReentrantLock3();
        Thread thread1 = new Thread(reentrantLock3);
        Thread thread2 = new Thread(reentrantLock3);
        thread1.start();
        thread2.start();

    }
}
