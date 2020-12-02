package com.yuan.middleware.design.chain;

import java.util.Date;

/**
 * Copyright(c) 2018 Sunyur.com, All Rights Reserved.
 * Project: sy
 * Author: yuanjiamin
 * CreateDate: 2020/12/1 9:02 下午
 * Description:
 */
public class RuleOne implements Filter {
    @Override
    public boolean execute(ChainEvent chainEvent, FilterChain filterChain) {
        Integer count = chainEvent.getCount();
        chainEvent.setCount(++count);
        chainEvent.setName(RuleOne.class.getSimpleName());
        chainEvent.setDate(new Date());
        System.out.println(chainEvent.toString());
        if (chainEvent.getCount() < 9) {
            System.out.println("继续执行下一个流程！");
            return filterChain.doFilter(chainEvent, filterChain);
        } else {
            System.out.println("条件不符合，结束流程！");
            return false;
        }
    }
}
