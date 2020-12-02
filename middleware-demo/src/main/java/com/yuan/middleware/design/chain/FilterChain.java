package com.yuan.middleware.design.chain;

import java.util.ArrayList;
import java.util.List;

/**
 * Copyright(c) 2018 Sunyur.com, All Rights Reserved.
 * Project: sy
 * Author: yuanjiamin
 * CreateDate: 2020/12/1 8:57 下午
 * Description: 责任链模式
 * https://www.cnblogs.com/ye-feng-yu/p/11107506.html
 * https://mp.weixin.qq.com/s/bc66hBy3yxcKAr09pmVqSg
 */
public class FilterChain {
    private List<Filter> filters = new ArrayList<>();
    private int index;

    public FilterChain addFilter(Filter filter) {
        filters.add(filter);
        return this;
    }

    public boolean doFilter(ChainEvent chainEvent, FilterChain filterChain) {
        if (index >= filters.size()) {
            return true;
        }
        Filter filter = filters.get(index);
        index++;
        return filter.execute(chainEvent, filterChain);
    }

    public static void main(String[] args) {
        FilterChain filterChain = new FilterChain();
        filterChain.addFilter(new RuleOne()).addFilter(new RuleTwo());
        ChainEvent chainEvent = new ChainEvent();
        chainEvent.setCount(2);
        filterChain.doFilter(chainEvent, filterChain);
    }
}
