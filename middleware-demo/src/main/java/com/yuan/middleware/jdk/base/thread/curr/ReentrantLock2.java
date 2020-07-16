package com.yuan.middleware.jdk.base.thread.curr;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

/**
 * ReentrantLock lockInterruptibly方法 可以对interrupt()进行响应 不会一直等待
 *
 * @author yuan
 * @date 2020/02/08
 */
public class ReentrantLock2 {
    public static void main(String[] args) {
        ReentrantLock lock = new ReentrantLock();

        Thread t1 = new Thread(() -> {
            try {
                //不会对interrupt()进行响应
                lock.lock();
                System.out.println("t1 start");
                TimeUnit.SECONDS.sleep(Integer.MAX_VALUE);
                System.out.println("t1 end");
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        });
        t1.start();

        Thread t2 = new Thread(() -> {
            boolean interrupted = false;
            try {
                //可以对interrupt()进行响应
                try {
                    lock.lockInterruptibly();
                } catch (InterruptedException e) {
                    interrupted = true;
                    System.out.println("t2 lock.lockInterruptibly() 被interrupt打断");
                }
                if (!interrupted){
                    System.out.println("t2 start");
                    TimeUnit.SECONDS.sleep(5);
                    System.out.println("t2 end");
                }
            } catch (InterruptedException e) {
            } finally {
                if (!interrupted) {
                    //只有获得锁才能解锁
                    lock.unlock();
                }
            }
        });
        t2.start();

        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //打断线程2的等待
        t2.interrupt();
    }

}
