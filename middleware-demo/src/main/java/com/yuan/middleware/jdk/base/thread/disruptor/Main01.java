package com.yuan.middleware.jdk.base.thread.disruptor;

import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.dsl.Disruptor;

import java.util.concurrent.Executors;

/**
 * @author yuanjm
 * @date 2020/9/18 3:49 下午
 */
public class Main01 {
    public static void main(String[] args) {
        // the factory for the event
        LongEventFactory factory = new LongEventFactory();

        // specify the size of the ring buffer, must be power of 2
        int bufferSize = 1024;

        // construct the disruptor
        Disruptor<LongEvent> disruptor = new Disruptor<>(factory, bufferSize, Executors.defaultThreadFactory());

        // connect the handler
        disruptor.handleEventsWith(new LongEventHandler());

        // start the disruptor, start all thread running
        disruptor.start();

        // get the ring buffer from the disruptor to be used for publishing
        RingBuffer<LongEvent> ringBuffer = disruptor.getRingBuffer();

        // grab the next sequence
        long sequence = ringBuffer.next();

        // get the entry in the disruptor
        LongEvent event = ringBuffer.get(sequence);

        // fill with data
        event.setVal(200L);

        // publish sequence event
        ringBuffer.publish(sequence);
    }
}
