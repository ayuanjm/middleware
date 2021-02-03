package com.yuan.middleware.jdk.base.thread.track;

import com.alibaba.ttl.TransmittableThreadLocal;

/**
 * Copyright(c) 2018 Sunyur.com, All Rights Reserved.
 * Project: message
 *
 * @author: yuanjiamin
 * CreateDate: 2021/1/14 4:38 下午
 * Description:
 */
public class Soul {
    public static void main(String[] args) {
        new TransmittableThreadLocal();
        new ThreadLocal<>();
        new InheritableThreadLocal<>();
        new Thread();
        System.out.println('a' == 'a');
        char[] chars = "a".toCharArray();
        System.out.println(chars);
    }
}