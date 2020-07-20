package com.yuan.middleware.separate.service.impl;

import com.yuan.middleware.separate.dao.MarketActivityMapper;
import com.yuan.middleware.separate.datasource.DataSource;
import com.yuan.middleware.separate.entity.MarketActivity;
import com.yuan.middleware.separate.service.DataSourceTransactional;
import com.yuan.middleware.separate.service.MarketActivityService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;

/**
 * @author yuanjm
 * @date 2020/7/16 3:43 下午
 */
@Transactional(rollbackFor = Exception.class)
@Service
public class MarketActivityServiceImpl implements MarketActivityService {
    @Resource
    private MarketActivityMapper activityMapper;

    @Resource
    private DataSourceTransactional dataSourceTransactional;

    @DataSource(name = "slave")
    public Object selectActivity(Long id) {
        MarketActivity marketActivity = activityMapper.selectByPrimaryKey(id);
        System.out.println(marketActivity.toString());
        activityMapper.insert(new MarketActivity());
        MarketActivity marketActivity1 = new MarketActivity();
        marketActivity1.setName("nn");
        marketActivity1.setStartTime(new Date());
        dataSourceTransactional.insert(marketActivity1);
        return marketActivity;
    }
}
