package com.yuan.middleware.jdk.base.event.listener;

import java.util.EventListener;

/**
 * Copyright(c) 2018 Sunyur.com, All Rights Reserved.
 * <P></P>
 * 事件监听者：
 * 事件监听者实际上就是一个类，这个类实现了某个事件监听器接口，它就可以作为一个事件监听者，对接受到的事件进行处理。
 * 事件监听器接口有多种，不同的事件监听器接口可以监听不同的事件，一个类可以实现一个事件监听接口，也可以实现多个监听接口
 *
 * @author YuanJiaMin
 * @date 2021/5/20 4:16 下午
 * @description
 */
@FunctionalInterface
public interface ClickEventListener extends EventListener {
    void click(ClickEvent o, String message);
}

