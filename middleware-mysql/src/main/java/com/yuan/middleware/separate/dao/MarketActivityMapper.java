package com.yuan.middleware.separate.dao;

import com.yuan.middleware.separate.entity.MarketActivity;

public interface MarketActivityMapper {
    int deleteByPrimaryKey(Long id);

    int insert(MarketActivity record);

    int insertSelective(MarketActivity record);

    MarketActivity selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(MarketActivity record);

    int updateByPrimaryKey(MarketActivity record);
}