package com.yuan.middleware.jdk.base.event.listener;

import java.util.HashSet;
import java.util.Set;

/**
 * Copyright(c) 2018 Sunyur.com, All Rights Reserved.
 * <P>https://www.jianshu.com/p/fc350b3b3e10</P>
 * 事件源：
 * 它是一个产生(或触发)事件的对象，比如文本框、按钮等。
 * 当这个事件源对象的某些状态以某种方式发生变化时，就会产生某种类型的事件(一个事件源可能会生成多个不同类型的事件)。
 * 另外，如果某个组件(对象)希望得到事件源产生的事件，就需要在这个事件源上注册，一个事件源是一个能够注册监听器并且为它们发送事件对象的对象。
 *
 * @author YuanJiaMin
 * @date 2021/5/20 4:19 下午
 * @description
 */
public class EventSource {
    private String name;

    private Set<ClickEventListener> listeners;

    public String getName() {
        return this.name;
    }

    public EventSource(String name) {
        this.name = name;
        this.listeners = new HashSet<>();
    }

    public void action(String msg) {
        System.out.printf("%s触发了事件:%s \n", this.name, msg);
        ClickEvent event = new ClickEvent(this);

        for (ClickEventListener listener : listeners) {
            listener.click(event, msg);
        }

    }

    public void addListener(ClickEventListener eventListener) {
        listeners.add(eventListener);
    }
}


