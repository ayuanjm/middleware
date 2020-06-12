package com.yuan.middleware.base.thread.curr;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author yjm
 * @date 2020/3/26 6:31 下午
 */
public class Condition2 {
    public static void main(String[] args) {
        final ReentrantLock lock = new ReentrantLock();
        final Condition condition = lock.newCondition();

        new Thread(() -> {
            lock.lock();
            try {
                //调用 #await()方法时，将会以当前线程构造成一个节点（Node），并将节点加入到等待队列的尾部
                condition.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(1);
            lock.unlock();
        }).start();

        new Thread(() -> {
            lock.lock();
            try {
                condition.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(2);
            lock.unlock();
        }).start();

        new Thread(() -> {
            lock.lock();
            try {
                condition.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(3);
            lock.unlock();
        }).start();

        new Thread(() -> {
            lock.lock();
            System.out.println(3);
            //唤醒等待队列第一个
            condition.signal();
            condition.signal();
            condition.signal();
            lock.unlock();
        }).start();
    }

}
