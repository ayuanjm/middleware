package com.yuan.middleware.spring.transaction;

import com.yuan.middleware.spring.entity.PlatformMessage;

/**
 * Copyright(c) 2018 Sunyur.com, All Rights Reserved.
 * <P></P>
 *
 * @author: YuanJiaMin
 * @date: 2021/3/19 9:16 下午
 * @describe:
 */
public interface PlatformMessageService {

    Integer updateMessage(PlatformMessage platformMessage);
}
