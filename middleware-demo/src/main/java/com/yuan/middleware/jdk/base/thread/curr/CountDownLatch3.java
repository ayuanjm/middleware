package com.yuan.middleware.jdk.base.thread.curr;

import java.util.concurrent.CountDownLatch;

/**
 * @author yuanjm
 * @date 2020/7/9 9:34 下午
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
