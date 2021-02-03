package com.yuan.middleware.jdk.base.thread.track;

import java.util.concurrent.TimeUnit;

/**
 * Copyright(c) 2018 Sunyur.com, All Rights Reserved.
 * Project: message
 *
 * @author: yuanjiamin
 * CreateDate: 2021/1/12 8:28 下午
 * Description:
 */
public class TrackRunnableOne implements Runnable {
    private String trackId = TrackIdUtil.TRACK_THREAD_LOCAL.get();

    @Override
    public void run() {
        System.out.println(Thread.currentThread().getName() + ":" + trackId);
        TrackIdUtil.TRACK_THREAD_LOCAL.set("two");
        new Thread(new TrackRunnableTwo()).start();
    }

    public static void main(String[] args) throws InterruptedException {
        TrackIdUtil.TRACK_THREAD_LOCAL.set("one");
        new Thread(new TrackRunnableOne()).start();
        TimeUnit.SECONDS.sleep(3);
        System.out.println(Thread.currentThread().getName() + ":" + TrackIdUtil.TRACK_THREAD_LOCAL.get());
        TrackIdUtil.TRACK_THREAD_LOCAL.remove();
    }
}
