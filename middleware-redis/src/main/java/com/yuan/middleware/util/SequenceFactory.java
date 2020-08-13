package com.yuan.middleware.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Random;
import java.util.UUID;

/**
 * @author yuanjm
 * @date 2020/7/13 5:02 下午
 */
@Slf4j
@Component
public class SequenceFactory {
    @Resource
    private RedisTemplate<String, String> redisTemplate;

    /**
     * redis分布式自增id
     *
     * @param key
     * @param delta
     * @return
     */
    public Long increment(String key, Long delta) {
        //默认自增1
        if (delta == null) {
            delta = 1L;
        }
        try {
            //根据redis的原子自增命令increment设置分布式id
            return redisTemplate.boundValueOps(key).increment(delta);
        } catch (Exception e) {
            log.error("redis 异常");
            // 若是redis宕机就采用uuid的方式
            int first = new Random(10).nextInt(8) + 1;
            int randNo = UUID.randomUUID().toString().hashCode();
            if (randNo < 0) {
                randNo = -randNo;
            }
            return Long.valueOf(first + String.format("%16d", randNo).trim());
        }
    }

    public static void main(String[] args) {
        int first = new Random(10).nextInt(8) + 1;
        int randNo = UUID.randomUUID().toString().hashCode();
        if (randNo < 0) {
            randNo = -randNo;
        }
        System.out.println(randNo);
        System.out.println(Long.valueOf(first + String.format("%16d", randNo).trim()));
    }

}
