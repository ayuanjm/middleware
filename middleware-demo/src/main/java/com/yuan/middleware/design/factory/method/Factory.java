package com.yuan.middleware.design.factory.method;

import com.yuan.middleware.design.factory.simple.Computer;

/**
 * Copyright(c) 2018 Sunyur.com, All Rights Reserved.
 * <P></P>
 *
 * @author: YuanJiaMin
 * @date: 2021/3/1 7:30 下午
 */
public interface Factory {
    Computer createComputer();

    static void main(String[] args) {
        Factory factory = new HpFactory();
        factory.createComputer().start();
    }
}
