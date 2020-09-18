package com.yuan.middleware.jdk.base.thread.disruptor;

import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.dsl.Disruptor;

import java.util.concurrent.Executors;

/**
 * @author yuanjm
 * @date 2020/9/18 4:28 下午
 */
public class Main02 {
    public static void main(String[] args) {
        int bufferSize = 1024;
        Disruptor<LongEvent> disruptor = new Disruptor<>(LongEvent::new, bufferSize, Executors.defaultThreadFactory());
        disruptor.handleEventsWith(
                (event, sequence, endOfBath) ->
                        System.out.println("[" + Thread.currentThread().getName() + "]" + " - " + event.toString() + " - 序号：" + sequence),
                (event, sequence, endOfBath) ->
                        System.out.println("[" + Thread.currentThread().getName() + "]" + " - " + event.toString() + " - 序号：" + sequence));
        disruptor.start();
        RingBuffer<LongEvent> ringBuffer = disruptor.getRingBuffer();
        ringBuffer.publishEvent((event, sequence) -> event.setVal(100L));
        ringBuffer.publishEvent((event, sequence, arg0, arg1) -> event.setVal(arg0 + arg1), 100L, 200L);
    }
}
