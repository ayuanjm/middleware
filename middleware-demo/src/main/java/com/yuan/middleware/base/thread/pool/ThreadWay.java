package com.yuan.middleware.base.thread.pool;

import lombok.SneakyThrows;

import java.util.Random;
import java.util.concurrent.*;

/**
 * 线程创建方式
 *
 * @author yuan
 * @date 2019/12/27
 */
public class ThreadWay {
    /**
     * 使用Runnable创建线程
     */
    static class MyRunnable implements Runnable {

        @Override
        public void run() {
            System.out.println(Thread.currentThread().getName());
        }

        public static void main(String[] args) {
            MyRunnable myRunnable = new MyRunnable();
            Thread thread = new Thread(myRunnable);
            thread.start();
        }
    }

    /**
     * 使用Thread创建线程
     */
    static class MyThread extends Thread {
        @Override
        public void run() {
            System.out.println(Thread.currentThread().getName());
        }

        public static void main(String[] args) {
            new MyThread().start();
        }
    }

    /**
     * 使用Callable和FutureTask创建线程并接收返回值
     */
    static class MyCallable implements Callable<Integer> {

        @Override
        public Integer call() {
            return new Random().nextInt(100);
        }

        public static void main(String[] args) throws ExecutionException, InterruptedException {
            //第一种方式
            ExecutorService executor = new ThreadPoolExecutor(
                    // 核心线程大小
                    30,
                    // 最大线程
                    50,
                    // 空闲等待销毁时间2分钟
                    2L,
                    TimeUnit.SECONDS,
                    // 保存等待执行的任务,基于数组结构的有界阻塞队列长度为 10
                    new ArrayBlockingQueue<>(3),
                    // 设置创建线程的工厂
                    Executors.defaultThreadFactory(),
                    // 线程池的拒绝策略 用调用者所在的线程来执行任务
                    new ThreadPoolExecutor.CallerRunsPolicy()
            );
            MyCallable task = new MyCallable();
            FutureTask<Integer> futureTask = new FutureTask<>(task);
            executor.execute(futureTask);
            executor.shutdown();
            //获取call的返回值
            System.out.println(futureTask.get());

            //第二种方式
            MyCallable myCallable = new MyCallable();
            FutureTask<Integer> integerFutureTask = new FutureTask<>(myCallable);
            new Thread(integerFutureTask).start();
            System.out.println(integerFutureTask.get());
        }
    }

    /**
     * 使用线程池执行线程任务
     */
    static class MyThreadPoolExecutor {
        public static void main(String[] args) {
            ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(// 核心线程大小
                    30,
                    // 最大线程
                    50,
                    // 空闲等待销毁时间2分钟
                    2L,
                    TimeUnit.SECONDS,
                    // 保存等待执行的任务,基于数组结构的有界阻塞队列长度为 10
                    new ArrayBlockingQueue<>(3),
                    // 设置创建线程的工厂
                    Executors.defaultThreadFactory(),
                    // 线程池的拒绝策略 用调用者所在的线程来执行任务
                    new ThreadPoolExecutor.CallerRunsPolicy());
            /**
             *  Executor接口中之定义了一个方法execute（Runnable command），
             *  该方法接收一个Runnable实例，它用来执行一个任务，任务即一个实现了Runnable接口的类。
             *  Thread implements Runnable
             *  RunnableFuture<V> extends Runnable
             *  FutureTask<V> implements RunnableFuture<V>
             */
            try {
                threadPoolExecutor.execute(() -> System.out.println(Thread.currentThread().getName()));
            } finally {
                threadPoolExecutor.shutdown();
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Thread thread = new Thread(new Runnable() {
            @SneakyThrows
            @Override
            public void run() {
                System.out.println(Thread.currentThread().getName());
                Thread.sleep(1000);
                System.out.println("thread sleep end");
            }
        });
        Thread thread1 = new Thread(new Runnable() {
            @SneakyThrows
            @Override
            public void run() {
                System.out.println(Thread.currentThread().getName());
                Thread.sleep(1000);
                System.out.println("thread sleep end");
            }
        });
        thread.start();
        thread1.start();
        thread.join();
        thread1.join();
        System.out.println(Thread.currentThread().getName());
    }
}
