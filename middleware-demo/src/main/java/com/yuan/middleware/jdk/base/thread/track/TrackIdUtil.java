package com.yuan.middleware.jdk.base.thread.track;

/**
 * Copyright(c) 2018 Sunyur.com, All Rights Reserved.
 * Project: message
 *
 * @author: yuanjiamin
 * CreateDate: 2021/1/12 8:32 下午
 * Description:
 */
public class TrackIdUtil {
    public static final ThreadLocal<String> TRACK_THREAD_LOCAL = new ThreadLocal<>();
}
