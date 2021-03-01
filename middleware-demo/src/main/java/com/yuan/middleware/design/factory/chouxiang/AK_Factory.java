package com.yuan.middleware.design.factory.chouxiang;

/**
 * Copyright(c) 2018 Sunyur.com, All Rights Reserved.
 * <P></P>
 *
 * @author: YuanJiaMin
 * @date: 2021/3/1 8:09 下午
 */
public class AK_Factory implements Factory{
    @Override
    public Gun createGun() {
        return new Ak();
    }

    @Override
    public Bullet createBullet() {
        return new AK_Bullet();
    }
}
