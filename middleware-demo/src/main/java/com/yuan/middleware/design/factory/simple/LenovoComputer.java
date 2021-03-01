package com.yuan.middleware.design.factory.simple;

/**
 * Copyright(c) 2018 Sunyur.com, All Rights Reserved.
 * <P></P>
 *
 * @author: YuanJiaMin
 * @date: 2021/2/24 10:25 上午
 */
public class LenovoComputer extends Computer {
    public LenovoComputer() {
        System.out.println("create LenovoComputer");
    }

    @Override
    public void start() {
        System.out.println("联想电脑启动");
    }
}
