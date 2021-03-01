package com.yuan.middleware.design.factory.simple;

/**
 * Copyright(c) 2018 Sunyur.com, All Rights Reserved.
 * <P>简单工厂 - 创建电脑的工厂类</P>
 *
 * @author: YuanJiaMin
 * @date: 2021/2/24 10:27 上午
 */
public class ComputerFactory {
    public static Computer createComputer(String type) {
        Computer mComputer = null;
        switch (type) {
            case "lenovo":
                mComputer = new LenovoComputer();
                break;
            case "hp":
                mComputer = new HpComputer();
                break;

        }
        return mComputer;
    }

    public static void main(String[] args) {
        ComputerFactory.createComputer("hp").start();
    }
}
