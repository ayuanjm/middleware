package com.yuan.middleware.design.factory.method;

import com.yuan.middleware.design.factory.simple.Computer;
import com.yuan.middleware.design.factory.simple.HpComputer;

/**
 * Copyright(c) 2018 Sunyur.com, All Rights Reserved.
 * <P></P>
 *
 * @author: YuanJiaMin
 * @date: 2021/3/1 7:32 下午
 */
public class HpFactory implements Factory {
    @Override
    public Computer createComputer() {
        return new HpComputer();
    }
}
