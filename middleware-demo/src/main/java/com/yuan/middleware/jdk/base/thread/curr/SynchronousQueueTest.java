package com.yuan.middleware.jdk.base.thread.curr;

import java.util.concurrent.SynchronousQueue;

/**
 * 队列内部不会存储元素，所以尽量避免使用add,offer此类立即返回的方法，除非有特殊需求
 *
 * @author yuanjm
 * @date 2020/7/13 11:06 上午
 */
public class SynchronousQueueTest {


    public static void main(String[] args) {
        SynchronousQueue<Character> a = new SynchronousQueue<>();
        char[] aI = "abcdefg".toCharArray();
        char[] aC = "ABCDEFG".toCharArray();
        new Thread(() -> {
            for (char c : aC) {
                try {
                    //没有拿到元素就一直阻塞，只有当队列生产了元素才能继续执行
                    System.out.print(a.take());
                    a.put(c);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();

        new Thread(() -> {
            for (char c : aI) {
                try {
                    //生产完了需要被拿走才能继续执行，否则阻塞
                    a.put(c);
                    System.out.print(a.take());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();


    }
}
