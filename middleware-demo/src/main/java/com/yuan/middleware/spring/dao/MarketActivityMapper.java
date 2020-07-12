package com.yuan.middleware.spring.dao;

import com.yuan.middleware.spring.entity.MarketActivity;

public interface MarketActivityMapper {
    int deleteByPrimaryKey(Long id);

    int insert(MarketActivity record);

    int insertSelective(MarketActivity record);

    MarketActivity selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(MarketActivity record);

    int updateByPrimaryKey(MarketActivity record);
}