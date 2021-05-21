package com.yuan.middleware.jdk.base.event.listener;

/**
 * Copyright(c) 2018 Sunyur.com, All Rights Reserved.
 * <P></P>
 *
 * @author YuanJiaMin
 * @date 2021/5/20 5:05 下午
 * @description
 */
public class Client {
    public static void main(String[] args) {
        EventSource eventSource = new EventSource("zuikc");
        eventSource.addListener((o, msg) -> {
            System.out.println("监听到事件" + o.getEventSource().getName() + ":" + msg);
        });
        eventSource.addListener((o, msg) -> {
            System.out.println("监听到事件" + o.getEventSource().getName() + ":" + msg);
        });
        eventSource.action("事件机制第二天作业");
    }
}
