package com.yuan.middleware.util;

import lombok.extern.slf4j.Slf4j;
import redis.clients.jedis.Jedis;

import java.util.Collections;

/**
 * 可以使用Redisson来实现分布式锁，这里是直接使用jedisCluster.set(key, value, "NX", "EX", expireSeconds);
 * 设置value和过期时间，这两个操作在jedis高版本里是原子性。
 * <p>
 * 首先，为了确保分布式锁可用，我们至少要确保锁的实现同时满足以下四个条件：
 * 1.**互斥性。**在任意时刻，只有一个客户端能持有锁。
 * 2.**不会发生死锁。**即使有一个客户端在持有锁的期间崩溃而没有主动解锁，也能保证后续其他客户端能加锁。
 * 3.**具有容错性。**只要大部分的Redis节点正常运行，客户端就可以加锁和解锁。
 * 4.**解铃还须系铃人。**加锁和解锁必须是同一个客户端，客户端自己不能把别人加的锁给解了。
 *
 * @author yuan
 * @date 2020/01/07
 */
@Slf4j
public class RedisTool {
    private static final String LOCK_SUCCESS = "OK";
    private static final String SET_IF_NOT_EXIST = "NX";
    private static final String SET_WITH_EXPIRE_TIME = "EX";
    private static final Long RELEASE_SUCCESS = 1L;


    /**
     * 尝试获取分布式锁
     *
     * @param lockKey
     * @param requestId
     * @param expireTime
     * @return
     */
    public static boolean tryAcquireLock(Jedis jedis, String lockKey, String requestId, int expireTime) {
        int count = 0;
        while (true) {
            boolean getLock = tryGetDistributedLock(jedis, lockKey, requestId, expireTime);
            if (getLock == true) {
                return true;
            } else {
                try {
                    //每次休眠count秒，尝试获取锁3次总共耗时6秒，3次后还是没有获取到锁，获取锁失败。(相当于自旋)
                    if (count == 3) {
                        return false;
                    }
                    count++;
                    log.info("没有获取到锁休眠" + count + "秒lockKey:{}", lockKey);
                    Thread.sleep(count * 1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    /**
     * 尝试获取分布式锁
     *
     * @param jedis      Redis客户端
     * @param lockKey    锁
     * @param requestId  请求标识
     * @param expireTime 超期时间
     * @return 是否获取成功
     */
    public static boolean tryGetDistributedLock(Jedis jedis, String lockKey, String requestId, int expireTime) {
        // NX是不存在时才set， XX是存在时才set， EX是秒，PX是毫秒
        String result = jedis.set(lockKey, requestId, SET_IF_NOT_EXIST, SET_WITH_EXPIRE_TIME, expireTime);
        if (LOCK_SUCCESS.equals(result)) {
            return true;
        }
        return false;

    }

    /**
     * 释放分布式锁并且释放jedis连接
     *
     * @param jedis     Redis客户端
     * @param lockKey   锁
     * @param requestId 请求标识
     * @return 是否释放成功
     */
    public static boolean releaseDistributedLock(Jedis jedis, String lockKey, String requestId) {

        Object result;
        try {
            String script = "if redis.call('get', KEYS[1]) == ARGV[1] then return redis.call('del', KEYS[1]) else return 0 end";
            result = jedis.eval(script, Collections.singletonList(lockKey), Collections.singletonList(requestId));
        } finally {
            //释放jedis连接资源
            JedisUtils.returnResource(jedis);
        }

        if (RELEASE_SUCCESS.equals(result)) {
            return true;
        }
        return false;

    }
}

