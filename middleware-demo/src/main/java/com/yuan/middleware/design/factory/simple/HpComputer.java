package com.yuan.middleware.design.factory.simple;

/**
 * Copyright(c) 2018 Sunyur.com, All Rights Reserved.
 * <P></P>
 *
 * @author: YuanJiaMin
 * @date: 2021/2/24 10:25 上午
 */
public class HpComputer extends Computer {
    public HpComputer() {
        System.out.println("create HpComputer");
    }

    @Override
    public void start() {
        System.out.println("惠普电脑启动");
    }
}
