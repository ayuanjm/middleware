package com.yuan.middleware.jdk.base.thread.track;

/**
 * Copyright(c) 2018 Sunyur.com, All Rights Reserved.
 * Project: message
 *
 * @author: yuanjiamin
 * CreateDate: 2021/1/12 8:28 下午
 * Description:
 */
public class TrackRunnableTwo implements Runnable {
    private String trackId = TrackIdUtil.TRACK_THREAD_LOCAL.get();

    @Override
    public void run() {
        System.out.println(Thread.currentThread().getName() + ":" + trackId);
        TrackIdUtil.TRACK_THREAD_LOCAL.set("three");
    }
}
