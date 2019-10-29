package com.yuan.middleware.demo.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author yuan
 */
@RestController
@Slf4j
public class ActivityController {

    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

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
        map.put("code", "200");
        map.put("msg", "操作成功！");
        log.info("风险评测source:{},userId:{}", source, userId);
        Assert.notNull(source, "风险评测source为空异常");
        Assert.notNull(userId, "风险评测userId为空异常");
        // 提示次数key
        String keyCount = "riskWarn" + source + userId;
        // 当天提示开始计时，一天提示一次，总共提示3次
        String keyTime = "riskWarnExpire" + source + userId;

        //获取当前时间
        String dateString = LocalDateTime.now().format(dateTimeFormatter);

        /**
         * 判断keyCount是否存在，存在返回false，
         * 不存在设置key值为keyCount，value值为1024，返回true，
         * 调用的是setNX命令 setNX k v,如果k存在返回0，不存在设置key=k,value=v,返回1
         */
        // 先设置keyExpire记录第一次调用接口触发弹窗的时间
        if (redisTemplate.boundValueOps(keyTime).setIfAbsent(dateString)) {
            // 自增key的value，调用底层命令incr,incr k 如果k存在则value值加1，不存在则设置key=k,value=1,返回自增后的value值
            redisTemplate.boundValueOps(keyCount).increment();
            //获取触发弹窗次数
            String count = redisTemplate.boundValueOps(keyCount).get();
            map.put("data", count);
            return map;
        } else {
            //获取前一次触发弹窗的时间
            String date = redisTemplate.boundValueOps(keyTime).get();
            //获取触发弹窗次数
            String count = redisTemplate.boundValueOps(keyCount).get();
            if (Integer.parseInt(count) >= 3) {
                // count >= 3 的时候前端不触发弹窗
                map.put("data", "4");
                return map;
            }
            //当前时间和上次获取弹窗时间同一天
            if (dateString.equals(date)) {
                //同一天不再触发弹窗
                map.put("data", count);
                return map;
            } else {
                //触发弹窗，次数加一,更新时间
                redisTemplate.boundValueOps(keyCount).increment();
                redisTemplate.boundValueOps(keyTime).set(dateString);
                //由于重新赋值需要重新获取
                count = redisTemplate.boundValueOps(keyCount).get();
//                if (Integer.parseInt(count) >= 3) {
//                    // 提示弹窗后设置keyCount过期时间为1年，当用户评测后就不会再设置redis值，一年后用户评测过期redis值也过期，次数又从0开始
//                    redisTemplate.boundValueOps(keyCount).expire(360, TimeUnit.DAYS);
//                    redisTemplate.boundValueOps(keyTime).expire(360, TimeUnit.DAYS);
//                }
                map.put("data", count);
                return map;
            }

        }
    }

    public static void main(String[] args) {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        //获取当前时间
        String dateString = df.format(new Date());
        System.out.println(dateString);
    }

}
