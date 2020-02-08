package com.yuan.middleware.base.thread.curr;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * ReentrantLock   lock tryLock
 *
 * @author yuan
 * @date 2020/02/08
 */
public class ReentrantLock1 {
    Lock lock = new ReentrantLock();

    void m1() {
        try {
            //synchronized (this)
            lock.lock();
            for (int i = 0; i < 3; i++) {
                TimeUnit.SECONDS.sleep(1);
                System.out.println(i);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            //必须手动解锁
            lock.unlock();
        }
    }

    void m2() {

        try {
            lock.lock();
            System.out.println("m2 ...");
        } finally {
            lock.unlock();
        }
    }

    /**
     * 使用tryLock进行尝试锁定，不管锁定与否方法都将继续执行
     * 可以根据tryLock的返回值判断是否锁定,编写业务逻辑
     * 也可以指定tyrLock的时间，由于tryLock(time)抛出异常，所以要注意unLock
     */
    void m3() {
        boolean locked = false;
        try {
            locked = lock.tryLock(5, TimeUnit.SECONDS);
            System.out.println("m3 ..." + locked);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (locked) {
                //只有当前线程拿到了锁才能解锁否则会抛出异常java.lang.IllegalMonitorStateException
                lock.unlock();
            }
        }
    }

    public static void main(String[] args) {
        ReentrantLock1 lock1 = new ReentrantLock1();
        new Thread(lock1::m1).start();
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
//        new Thread(() -> lock1.m2()).start();
        new Thread((lock1::m3)).start();
    }
}
