package com.yuan.middleware.spring.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * GET（SELECT）：从服务器取出资源（一项或多项）
 * POST（CREATE）：在服务器新建一个资源
 * PUT（UPDATE）：在服务器更新资源（客户端提供完整资源数据）
 * PATCH（UPDATE）：在服务器更新资源（客户端提供需要修改的资源数据）
 * DELETE（DELETE）：从服务器删除资源
 *
 * @author yuan
 */
@RestController
@Slf4j
@RequestMapping("/redis")
public class RedisController {
    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @GetMapping(value = "/getValue")
    public Object getValue(String key) {
        Map<String, Object> map = new HashMap<>(16);
        log.info("获取指定key的value值:" + key);
        String value = redisTemplate.boundValueOps(key).get();
        //获取过期时间
        Long expireTime = redisTemplate.boundValueOps(key).getExpire();
        map.put("expireTime", expireTime);
        map.put("key", key);
        map.put("value", value);
        return map;
    }

    @DeleteMapping(value = "/deleteKey")
    public Object deleteKey(String key) {
        Map<String, Object> map = new HashMap<>(16);
        log.info("删除指定key:" + key);
        Object value = redisTemplate.boundValueOps(key).get();
        redisTemplate.delete(key);
        map.put("key", key);
        map.put("value", value);
        return map;
    }

    /**
     * @param key
     * @param value    默认String类型
     * @param time     过期时间
     * @param timeType 过期时间类型
     * @return
     */
    @PostMapping(value = "/setKey")
    public Object setKey(String key, String value, @RequestParam long time, String timeType) {
        Map<String, Object> map = new HashMap<>(16);
        log.info("设置key入参:{key:" + key + ",value:" + value + ",time:" + time + ",timeType" + timeType + "}");
        redisTemplate.boundValueOps(key).set(value);
        String tempValue = redisTemplate.boundValueOps(key).get();
        map.put("key", key);
        map.put("value", tempValue);
        //time没有传参不设置过期时间
        if (time == 0) {
            return map;
        }
        setExpire(key, time, tempValue);
        //获取过期时间
        Long expireTime = redisTemplate.boundValueOps(key).getExpire();
        map.put("expireTime", expireTime);
        return map;
    }

    private void setExpire(String key, long time, String tempValue) {
        switch (tempValue) {
            case "minutes":
                redisTemplate.boundValueOps(key).expire(time, TimeUnit.MINUTES);
                break;
            case "hours":
                redisTemplate.boundValueOps(key).expire(time, TimeUnit.HOURS);
                break;
            case "days":
                redisTemplate.boundValueOps(key).expire(time, TimeUnit.DAYS);
                break;
            default:
                redisTemplate.boundValueOps(key).expire(time, TimeUnit.SECONDS);
                break;
        }
    }
}
