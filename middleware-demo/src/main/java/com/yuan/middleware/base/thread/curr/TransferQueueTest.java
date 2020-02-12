package com.yuan.middleware.base.thread.curr;

import java.util.concurrent.LinkedTransferQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TransferQueue;

/**
 * @author yuan
 * @date 2020/02/12
 */
public class TransferQueueTest {
    public static void main(String[] args) throws InterruptedException {
        char[] aI = "abcdefg".toCharArray();
        char[] aC = "ABCDEFG".toCharArray();

        TransferQueue<Character> queue = new LinkedTransferQueue<>();
        new Thread(() -> {
            for (char c : aI) {
                try {
                    //没有拿到元素就一直阻塞，只有当队列生产了元素才能继续执行
                    System.out.print(queue.take());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                try {
                    //生产完了需要被拿走才能继续执行，否则阻塞
                    queue.transfer(c);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "t1").start();
        TimeUnit.SECONDS.sleep(1);
        new Thread(() -> {
            for (char c : aC) {
                try {
                    queue.transfer(c);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                try {
                    System.out.print(queue.take());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        }, "t2").start();

    }
}
