package com.yuan.middleware.design.chain;

/**
 * Copyright(c) 2018 Sunyur.com, All Rights Reserved.
 * Project: sy
 * Author: yuanjiamin
 * CreateDate: 2020/12/1 8:57 下午
 * Description:
 */
public interface Filter {
    boolean execute(ChainEvent chainEvent,FilterChain filterChain);
}
