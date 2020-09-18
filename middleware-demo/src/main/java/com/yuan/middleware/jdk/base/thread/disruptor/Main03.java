package com.yuan.middleware.jdk.base.thread.disruptor;

import com.lmax.disruptor.BlockingWaitStrategy;
import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.ProducerType;

import java.util.concurrent.*;

/**
 * @author yuanjm
 * @date 2020/9/18 4:49 下午
 */
public class Main03 {
    public static void main(String[] args) {
        int bufferSize = 1024;
        Disruptor<LongEvent> disruptor = new Disruptor<>(LongEvent::new,
                bufferSize,
                Executors.defaultThreadFactory(),
                ProducerType.MULTI,
                new BlockingWaitStrategy());
        disruptor.handleEventsWith((event, sequence, endOfBath) ->
                System.out.println("[" + Thread.currentThread().getName() + "]" + " - " + event.toString() + " - 序号：" + sequence));
        disruptor.start();
        RingBuffer<LongEvent> ringBuffer = disruptor.getRingBuffer();

        ThreadPoolExecutor poolExecutor = new ThreadPoolExecutor(5,
                50,
                60L,
                TimeUnit.SECONDS,
                //由于CyclicBarrier的存在所以阻塞队列需要为0,也就是带缓冲的阻塞队列，因为只有阻塞队列满了才会创建最大线程
                new SynchronousQueue<>(),
                Executors.defaultThreadFactory(),
                new ThreadPoolExecutor.AbortPolicy());

        final int threadCount = 50;
        CyclicBarrier cyclicBarrier = new CyclicBarrier(threadCount);

        for (int i = 0; i < threadCount; i++) {
            int finalI = i;
            poolExecutor.execute(() -> {
                try {
                    System.out.println(finalI);
                    cyclicBarrier.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (BrokenBarrierException e) {
                    e.printStackTrace();
                }
                for (int j = 0; j < 100; j++) {
                    ringBuffer.publishEvent((event, sequence, arg0, arg1) -> event.setVal(arg0 + arg1), 100L, 200L);
                }
            });
        }

    }
}
