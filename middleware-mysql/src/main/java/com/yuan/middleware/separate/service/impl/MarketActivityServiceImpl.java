package com.yuan.middleware.separate.service.impl;

import com.yuan.middleware.separate.dao.MarketActivityMapper;
import com.yuan.middleware.separate.datasource.DataSource;
import com.yuan.middleware.separate.entity.MarketActivity;
import com.yuan.middleware.separate.service.MarketActivityService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author yuanjm
 * @date 2020/7/16 3:43 下午
 */
@Service
public class MarketActivityServiceImpl implements MarketActivityService {
    @Resource
    private MarketActivityMapper activityMapper;

    @Override
    @DataSource(name = "slave")
    public Object selectActivity(Long id) {
        MarketActivity marketActivity = activityMapper.selectByPrimaryKey(id);
        System.out.println(marketActivity.toString());
        return marketActivity;
    }
}
