package com.yuan.middleware.spring.service.impl;

import com.yuan.middleware.spring.service.SeckillService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

/**
 * @author yuan
 */
@Slf4j
public class SeckillServiceImpl implements SeckillService {
    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Override
    public Object seckill(String userId) {
//        Integer
        return null;
    }
}
