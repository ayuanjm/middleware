package com.yuan.middleware.design.chain;

import java.util.Date;

/**
 * Copyright(c) 2018 Sunyur.com, All Rights Reserved.
 * Project: sy
 * Author: yuanjiamin
 * CreateDate: 2020/12/1 9:05 下午
 * Description:
 */
public class RuleTwo implements Filter {
    @Override
    public boolean execute(ChainEvent chainEvent, FilterChain filterChain) {
        chainEvent.setName(RuleTwo.class.getSimpleName());
        chainEvent.setDate(new Date());
        System.out.println(chainEvent.toString());
        filterChain.doFilter(chainEvent, filterChain);
        return true;
    }
}
