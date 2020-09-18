package com.yuan.middleware.jdk.base.thread.disruptor;

import com.lmax.disruptor.EventHandler;
import com.lmax.disruptor.ExceptionHandler;
import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.dsl.Disruptor;

import java.util.concurrent.Executors;

/**
 * @author yuanjm
 * @date 2020/9/18 4:28 下午
 */
public class Main04 {
    public static void main(String[] args) {
        int bufferSize = 1024;
        Disruptor<LongEvent> disruptor = new Disruptor<>(LongEvent::new, bufferSize, Executors.defaultThreadFactory());
        EventHandler h1 = ((event, sequence, endOfBath) -> {
            System.out.println("[" + Thread.currentThread().getName() + "]" + " - " + event.toString() + " - 序号：" + sequence);
            throw new Exception();
        });

        LongEventHandler h2 = new LongEventHandler();

        disruptor.handleEventsWith(h1, h2);

        disruptor.handleExceptionsFor(h1).with(new ExceptionHandler<LongEvent>() {
            @Override
            public void handleEventException(Throwable ex, long sequence, LongEvent event) {
                ex.printStackTrace();
            }

            @Override
            public void handleOnStartException(Throwable ex) {
                System.out.println("Exception Start To Handle!");
            }

            @Override
            public void handleOnShutdownException(Throwable ex) {

            }
        });
        disruptor.start();
        RingBuffer<LongEvent> ringBuffer = disruptor.getRingBuffer();
        ringBuffer.publishEvent((event, sequence) -> event.setVal(100L));
        ringBuffer.publishEvent((event, sequence) -> event.setVal(100L));
    }
}
