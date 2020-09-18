package com.yuan.middleware.jdk.base.thread.disruptor;


import com.lmax.disruptor.EventHandler;

/**
 * @author yuanjm
 * @date 2020/9/18 3:40 下午
 */
public class LongEventHandler implements EventHandler<LongEvent> {

    private static long count = 0;

    /**
     * @param event
     * @param sequence   RingBuffer的序号
     * @param endOfBatch 是否最后一个元素
     */
    @Override
    public void onEvent(LongEvent event, long sequence, boolean endOfBatch) {
        count++;
        System.out.println("[" + Thread.currentThread().getName() + "]" + " - " + event.toString() + " - 序号：" + sequence);
    }
}
