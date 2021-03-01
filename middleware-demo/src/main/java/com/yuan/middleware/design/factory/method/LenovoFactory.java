package com.yuan.middleware.design.factory.method;

import com.yuan.middleware.design.factory.simple.Computer;
import com.yuan.middleware.design.factory.simple.LenovoComputer;

/**
 * Copyright(c) 2018 Sunyur.com, All Rights Reserved.
 * <P></P>
 *
 * @author: YuanJiaMin
 * @date: 2021/3/1 7:38 下午
 */
public class LenovoFactory implements Factory {
    @Override
    public Computer createComputer() {
        return new LenovoComputer();
    }
}
