package com.yuan.middleware.design.factory.chouxiang;

/**
 * Copyright(c) 2018 Sunyur.com, All Rights Reserved.
 * <P></P>
 *
 * @author: YuanJiaMin
 * @date: 2021/3/1 8:10 下午
 */
public class M4_Factory implements Factory {
    @Override
    public Gun createGun() {
        return new M4();
    }

    @Override
    public Bullet createBullet() {
        return new M4_Bullet();
    }
}
