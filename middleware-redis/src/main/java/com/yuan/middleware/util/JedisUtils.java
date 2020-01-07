package com.yuan.middleware.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.ReflectionUtils;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;


import javax.annotation.Resource;
import java.lang.reflect.Field;

/**
 * 通过反射获取JedisPool，再通过jedisPool获取Jedis实例
 * InitializingBean接口为bean提供了初始化方法的方式，它只包括afterPropertiesSet方法，凡是继承该接口的类，
 * 在初始化bean的时候都会执行该方法。
 *
 * @author yuan
 * @date 2020/01/07
 */
@Slf4j
@Component
public class JedisUtils implements InitializingBean {
    @Resource
    private JedisConnectionFactory jedisConnectionFactory;

    private JedisPool jedisPool;

    /**
     * 获取Jedis实例
     *
     * @return
     */
    public Jedis getJedis() {
        // 记的关闭jedis
        if (jedisPool == null) {
            afterPropertiesSet();
        }
        return jedisPool.getResource();
    }

    /**
     * 释放jedis连接资源
     *
     * @param jedis
     */
    public static void returnResource(Jedis jedis) {
        if (jedis != null) {
            jedis.close();
        }
    }

    /**
     * 通过反射获取jedisPool
     */
    @Override
    public void afterPropertiesSet() {
        Field poolField = ReflectionUtils.findField(JedisConnectionFactory.class, "pool");
        ReflectionUtils.makeAccessible(poolField);
        jedisPool = (JedisPool) ReflectionUtils.getField(poolField, jedisConnectionFactory);
        log.info("初始化bean获取jedisPool:{}", jedisPool);
    }
}


