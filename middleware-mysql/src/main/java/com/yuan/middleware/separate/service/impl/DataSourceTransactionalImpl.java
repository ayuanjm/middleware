package com.yuan.middleware.separate.service.impl;

import com.yuan.middleware.separate.dao.MarketActivityMapper;
import com.yuan.middleware.separate.datasource.DataSource;
import com.yuan.middleware.separate.entity.MarketActivity;
import com.yuan.middleware.separate.service.DataSourceTransactional;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * @author yuanjm
 * @date 2020/7/20 3:18 下午
 */
@Transactional
@Service
public class DataSourceTransactionalImpl implements DataSourceTransactional {
    @Resource
    private MarketActivityMapper mapper;

    @DataSource(name = "master")
    @Override
    public void insert(MarketActivity marketActivity) {
        mapper.insert(marketActivity);
    }

    @Override
    public Object select(Long l) {
        return mapper.selectByPrimaryKey(l);
    }
}
