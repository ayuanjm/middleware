package com.yuan.middleware.jdk.base.event.listener;

import java.util.EventObject;

/**
 * Copyright(c) 2018 Sunyur.com, All Rights Reserved.
 * <P></P>
 * 事件：
 * 它是承载事件源状态改变时的信息对象。也可以说，事件是事件源事件监听器传输事件源状态信息的载体。
 * 在用户与GUI组件进行交互时就会生成事件，就比如说当鼠标在面板中移动时，就会生成一个鼠标移动事件的对象，而这个对象保存着当前鼠标在面板中位置信息
 *
 * @author YuanJiaMin
 * @date 2021/5/20 4:18 下午
 * @description 事件对象 拥有事件源
 */
public class ClickEvent extends EventObject {
    /**
     * 事件源
     */
    private EventSource eventSource;

    /**
     * Constructs a prototypical Event.
     *
     * @param source The object on which the Event initially occurred.
     * @throws IllegalArgumentException if source is null.
     */
    public ClickEvent(EventSource source) {
        super(source);
        this.eventSource = source;
    }

    public EventSource getEventSource() {
        return eventSource;
    }
}
