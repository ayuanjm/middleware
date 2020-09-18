package com.yuan.middleware.jdk.base.thread.disruptor;

import com.lmax.disruptor.EventFactory;

/**
 * @author yuanjm
 * @date 2020/9/18 3:37 下午
 */
public class LongEventFactory implements EventFactory<LongEvent> {
    @Override
    public LongEvent newInstance() {
        return new LongEvent();
    }
}
