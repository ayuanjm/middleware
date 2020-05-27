package com.yuan.middleware.base.thread.curr;

import java.util.concurrent.LinkedBlockingQueue;

/**
 * @author yuanjm
 * @date 2020/5/23 6:09 下午
 */
public class LinkedBlockingQueue1 {
    public static void main(String[] args) throws InterruptedException {
        LinkedBlockingQueue<Object> objects = new LinkedBlockingQueue<>(10);
        for (int i = 0;i<20;i++){
            System.out.println(i);
            objects.put(1);
        }
    }
}
