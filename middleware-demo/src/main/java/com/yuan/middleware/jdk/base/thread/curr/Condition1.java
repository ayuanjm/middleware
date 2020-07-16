package com.yuan.middleware.jdk.base.thread.curr;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author yuanjm
 * @date 2020/5/30 4:11 下午
 */
public class Condition1 {
    public static void main(String[] args) throws InterruptedException {

        ReentrantLock reentrantLock = new ReentrantLock(true);
        Condition condition = reentrantLock.newCondition();
        new Thread(
                () -> {
                    reentrantLock.lock();
                    System.out.println(1);
                    try {
                        condition.await();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println(2);
                    reentrantLock.unlock();
                }
        ).start();
        TimeUnit.SECONDS.sleep(3);
        new Thread(
                () -> {
                    reentrantLock.lock();
                    System.out.println(3);
                    condition.signal();
                    System.out.println(4);
                    reentrantLock.unlock();
                }
        ).start();
    }

}
