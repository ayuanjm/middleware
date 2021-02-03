package com.yuan.middleware.jdk.interview;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author yuanjm
 * @date 2020/7/22 9:14 下午
 */
@RestController
public class QuestionOne {
    /**
     * 0元抢购礼品活动，每天凌晨0点整，放出1000件礼品。
     * // * 每个用户每天最多只能买到3件（超过3件则交易失败）。
     * // * 代码工作环境为单机高并发：
     * // * 实现一个类，完成以上功能：
     */

    @Resource
    private RedisTemplate<String, Integer> redisTemplate;

    @RequestMapping("/spike")
    public Object spike(String userId) {
        //一次只能一个用户买，太惨了。。。
        synchronized (QuestionOne.class) {
            //总的商品数量,假设每天凌晨将数量置换为0
            Integer count = redisTemplate.boundValueOps("count").get();
            if (count >= 1000) {
                return "商品已经抢完";
            }
            redisTemplate.boundValueOps("count").increment();
            Integer userCount = redisTemplate.boundValueOps("spike" + userId).get();
            if (userCount < 3) {
                redisTemplate.boundValueOps("spike" + userId).increment();
            } else {
                return "商品已经抢完";
            }
            return "购买成功";
        }

    }

}
