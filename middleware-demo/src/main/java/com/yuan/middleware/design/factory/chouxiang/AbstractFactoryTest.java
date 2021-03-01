package com.yuan.middleware.design.factory.chouxiang;

/**
 * Copyright(c) 2018 Sunyur.com, All Rights Reserved.
 * <p>
 * https://www.imooc.com/article/31360
 * https://zhuanlan.zhihu.com/p/67360074
 * </P>
 *
 * @author: YuanJiaMin
 * @date: 2021/3/1 8:11 下午
 */
public class AbstractFactoryTest {
    public static void main(String[] args) {
        Factory factory = new AK_Factory();
        factory.createBullet().load();
        factory.createGun().shooting();
    }
}
