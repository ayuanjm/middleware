package com.yuan.middleware.separate.service;

import com.yuan.middleware.separate.entity.MarketActivity;

/**
 * @author yuanjm
 * @date 2020/7/20 3:18 下午
 */
public interface DataSourceTransactional {
    void insert(MarketActivity marketActivity);

    Object select(Long l);
}
