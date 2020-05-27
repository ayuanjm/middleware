package com.yuan.middleware.base.thread.pool;

import java.util.concurrent.*;

/**
 * 线程池:
 * 如果当前线程池中的线程数目小于corePoolSize，则每来一个任务，就会创建一个线程去执行这个任务；
 * 如果当前线程池中的线程数目>=corePoolSize，则每来一个任务，会尝试将其添加到任务缓存队列当中，若添加成功，则该任务会等待空闲线程将其取出去执行；若添加失败（一般来说是任务缓存队列已满），则会尝试创建新的线程去执行这个任务；
 * 如果当前线程池中的线程数目达到maximumPoolSize，则会采取任务拒绝策略进行处理；
 * 如果线程池中的线程数量大于 corePoolSize时，如果某线程空闲时间超过keepAliveTime，线程将被终止，直至线程池中的线程数目不大于corePoolSize；如果允许为核心池中的线程设置存活时间，那么核心池中的线程空闲时间超过keepAliveTime，线程也会被终止。
 * <p>
 * 有界队列：就是有固定大小的队列。比如设定了固定大小的 LinkedBlockingQueue，又或者大小为 0，只是在生产者和消费者中做中转用的 SynchronousQueue。
 * 无界队列：指的是没有设置固定大小的队列。这些队列的特点是可以直接入列，直到溢出。当然现实几乎不会有到这么大的容量（超过 Integer.MAX_VALUE），所以从使用者的体验上，就相当于 “无界”。比如没有设定固定大小的 LinkedBlockingQueue。
 * <p>
 * 7大参数:
 * int corePoolSize, ==> 核心池子的大小
 * int maximumPoolSize, ==> 最大大小！
 * long keepAliveTime,  ==> 连接时间！
 * TimeUnit unit, ==> 时间单位！
 * BlockingQueue<Runnable> workQueue, ===> 阻塞队列
 * ThreadFactory threadFactory, ===> 创建线程！
 * RejectedExecutionHandler handler ===>拒绝策略
 * <p>
 * 1、corePoolSize
 * 线程池中核心线程的数量。当提交一个任务时，线程池会新建一个线程来执行任务，直到当前线程数等于corePoolSize。如果调用了线程池的prestartAllCoreThreads()方法，线程池会提前创建并启动所有基本线程。
 * <p>
 * 2、maximumPoolSize
 * 线程池中允许的最大线程数。线程池的阻塞队列满了之后，如果还checkAmount有任务提交，如果当前的线程数小于maximumPoolSize，则会新建线程来执行任务。注意，如果使用的是无界队列，该参数也就没有什么效果了。
 * <p>
 * 3、keepAliveTime
 * 线程空闲的时间。线程的创建和销毁是需要代价的。线程执行完任务后不会立即销毁，而是继续存活一段时间：keepAliveTime。默认情况下，该参数只有在线程数大于corePoolSize时才会生效。
 * <p>
 * 4、unit
 * keepAliveTime的单位。TimeUnit
 * <p>
 * 5、workQueue
 * 用来保存等待执行的任务的阻塞队列，等待的任务必须实现Runnable接口。我们可以选择如下几种：
 * ArrayBlockingQueue：基于数组结构的有界阻塞队列，FIFO
 * LinkedBlockingQueue：基于链表结构的有界阻塞队列，FIFO。
 * SynchronousQueue：不存储元素的阻塞队列，每个插入操作都必须等待一个移出操作，反之亦然
 * PriorityBlockingQueue：具有优先界别的阻塞队列
 * <p>
 * 6、handler
 * RejectedExecutionHandler，线程池的拒绝策略。所谓拒绝策略，是指将任务添加到线程池中时，线程池拒绝该任务所采取的相应策略。当向线程池中提交任务时，如果此时线程池中的线程已经饱和了，而且阻塞队列也已经满了，则线程池会选择一种拒绝策略来处理该任务。
 * 线程池提供了四种拒绝策略：
 * AbortPolicy：直接抛出异常，默认策略；
 * CallerRunsPolicy：用调用者所在的线程来执行任务；
 * DiscardOldestPolicy：丢弃阻塞队列中靠最前的任务，并执行当前任务；
 * DiscardPolicy：直接丢弃任务；
 * 当然我们也可以实现自己的拒绝策略，例如记录日志等等，实现RejectedExecutionHandler接口即可。
 * <p>
 * 7、threadFactory
 * 用于设置创建线程的工厂。该对象可以通过Executors.defaultThreadFactory()
 *
 * @author yuan
 * @date 2019/12/09
 */
public class ThreadPool {
    public static void main(String[] args) {
        test();
    }

    /**
     * 我们生产中只能使用自定义的。
     * 线程池不允许使用 Executors 去创建，而是通过 ThreadPoolExecutor 的方式
     * 说明： Executors 返回的线程池对象的弊端如下：
     * 1） FixedThreadPool 和 SingleThreadPool :
     * 允许的请求队列长度为 Integer.MAX_VALUE ，可能会堆积大量的请求，从而导致 OOM 。
     * 2） CachedThreadPool 和 ScheduledThreadPool :
     * 允许的创建线程数量为 Integer.MAX_VALUE ，可能会创建大量的线程，从而导致 OOM 。
     * ExecutorService threadPool = Executors.newFixedThreadPool(5);
     * ExecutorService threadPool = Executors.newSingleThreadExecutor();
     * ExecutorService threadPool = Executors.newCachedThreadPool();
     */
    private static void test() {
        /**
         * 第一个请求进来时会创建1个核心线程，然后3个进入阻塞队列，然后创建2个最大线程，后面进来的4个被拒绝策略 拒绝并抛出异常RejectedExecutionException
         */
        ExecutorService threadPool = new ThreadPoolExecutor(
                1,
                3,
                2L,
                TimeUnit.SECONDS,
                new ArrayBlockingQueue<>(3),
                Executors.defaultThreadFactory(),
                new ThreadPoolExecutor.AbortPolicy()
        );
        try {
            for (int i = 1; i <= 10; i++) {
                threadPool.execute(() -> {
                    try {
                        Thread.sleep(3000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println(Thread.currentThread().getName() + "办理业务");
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //自己new出来的线程池使用后必须关闭，从spring容器中取出来的单例 线程池对象 使用后不能关闭
            System.out.println("...........");
            threadPool.shutdown();
        }
    }
}
