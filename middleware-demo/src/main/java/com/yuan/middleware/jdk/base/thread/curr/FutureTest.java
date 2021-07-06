package com.yuan.middleware.jdk.base.thread.curr;

import java.util.concurrent.*;

/**
 * Copyright(c) 2018 Sunyur.com, All Rights Reserved.
 * <P></P>
 *
 * @author YuanJiaMin
 * @date 2021/4/25 11:54 上午
 * @description
 */
public class FutureTest {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        // 异步操作 可以用一个线程池
        ExecutorService executor = Executors.newFixedThreadPool(2);
        // future有返回值的异步线程
        Future<String> future = executor.submit(() -> {
            System.out.println("future 业务逻辑处理中:" + Thread.currentThread().getName());
            TimeUnit.SECONDS.sleep(5);
            return "future return value end";
        });
        // 没有返回值的异步线程
        executor.execute(() -> {
            System.out.println("no return value start:" + Thread.currentThread().getName());
            try {
                TimeUnit.SECONDS.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("no return value end:" + Thread.currentThread().getName());
        });
        System.out.println("main 请求完毕，等待异步线程 数据准备中");
        //如果此时submit()方法没有执行完成，则依然会等待
        System.out.println("future数据:" + future.get());
        System.out.println("main end");
        executor.shutdown();
    }
}
