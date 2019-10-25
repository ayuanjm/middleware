package com.yuan.middleware.demo.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author yuan
 */
@RestController
@Slf4j
public class ActivityController {
    private static final String EXPIRE_TIME_VALUE = "expireTime";
    @Autowired
    RedisTemplate<String, String> redisTemplate;

    /**
     * 风险评测
     *
     * @param source 来源 app/pc
     * @param userId 用户id
     * @return
     */
    @RequestMapping(value = "/riskWarn")
    public Object riskWarn(String source, String userId) {
        Map<String, Object> map = new HashMap<>(16);
        log.info("风险评测source:{},userId:{}", source, userId);
        Assert.notNull(source, "风险评测source为空异常");
        Assert.notNull(userId, "风险评测userId为空异常");
        // 提示次数key
        String keyCount = "riskWarn" + source + userId;
        // 当天提示开始计时，一天提示一次，总共提示3次
        String keyExpire = "riskWarnExpire" + source + userId;
        /**
         * 判断keyCount是否存在，存在返回false，
         * 不存在设置key值为keyCount，value值为1024，返回true，
         * 调用的是setNX命令 setNX k v,如果k存在返回0，不存在设置key=k,value=v,返回1
         */
        // 先设置keyExpire记录第一次调用接口触发弹窗的时间
        if (redisTemplate.boundValueOps(keyExpire).setIfAbsent(EXPIRE_TIME_VALUE)) {
            /**
             *  设置key的过期时间，如果key存在 设置过期时间 返回true，key不存在返回false,
             *  调用底层命令expire，expire k 10 如果k存在则设置过期时间10秒，不存在返回(nil)
             */
            // 一天提示一次
            redisTemplate.boundValueOps(keyExpire).expire(1, TimeUnit.DAYS);
            // 自增key的value，调用底层命令incr,incr k 如果k存在则value值加1，不存在则设置key=k,value=1,返回自增后的value值
            redisTemplate.boundValueOps(keyCount).increment();
            // 提示弹窗后设置keyCount过期时间为1年，当用户评测后就不会再设置redis值，一年后用户评测过期redis值也过期，次数又从0开始
            redisTemplate.boundValueOps(keyCount).expire(365, TimeUnit.DAYS);

            // value < 4的时候前端触发弹窗
            String value = redisTemplate.boundValueOps(keyCount).get();
            map.put("data", value);
        } else {
            map.put("data", 4);
        }

        map.put("code", "200");
        map.put("msg", "操作成功！");
        return map;
    }
}
