package com.yuan.middleware.base.thread.curr.notice;

import java.util.concurrent.CountDownLatch;

/**
 * CountDownLatch 是通过一个计数器来实现的，当我们在 new 一个 CountDownLatch 对象的时候，需要带入该计数器值，该值就表示了线程的数量。
 * 每当一个线程完成自己的任务后，计数器的值就会减 1 。
 * 当计数器的值变为0时，就表示所有的线程均已经完成了任务，然后就可以恢复等待的线程继续执行了。
 * <p>
 * CountDownLatch 内部通过共享锁实现。
 * 在创建 CountDownLatch 实例时，需要传递一个int型的参数：count，该参数为计数器的初始值，也可以理解为该共享锁可以获取的总次数。setState(count);;
 * 当某个线程调用 #await() 方法，程序首先判断 count 的值是否为 0 ，如果不为 0 的话，则会一直等待直到为 0 为止。
 * 当其他线程调用 #countDown() 方法时，则执行释放共享锁状态，使 count 值 - 1。
 * 当在创建 CountDownLatch 时初始化的 count 参数，必须要有 count 线程调用#countDown() 方法，才会使计数器 count 等于 0 ，锁才会释放，前面等待的线程才会继续运行。
 * 注意 CountDownLatch 不能回滚重置。
 *
 * @author yuanjm
 * @date 2020/6/27 11:10 上午
 */
public class CountDownLatch3 {

    private static CountDownLatch countDownLatch = new CountDownLatch(5);

    /**
     * Boss线程，等待员工到达开会
     */
    static class BossThread extends Thread {
        @Override
        public void run() {
            System.out.println("Boss在会议室等待，总共有" + countDownLatch.getCount() + "个人开会...");
            try {
                //Boss等待
                countDownLatch.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            System.out.println("所有人都已经到齐了，开会吧...");
        }
    }

    // 员工到达会议室线程
    static class EmpleoyeeThread extends Thread {
        @Override
        public void run() {
            System.out.println(Thread.currentThread().getName() + "，到达会议室....");
            //员工到达会议室 count - 1
            countDownLatch.countDown();
        }
    }

    public static void main(String[] args) {
        //Boss线程启动
        new BossThread().start();
        long count = countDownLatch.getCount();
        for (int i = 0; i < count; i++) {
            new EmpleoyeeThread().start();
        }
    }
}
