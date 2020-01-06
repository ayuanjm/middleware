package com.yuan.middleware.service;

import org.redisson.api.RLock;

import java.util.concurrent.TimeUnit;

/**
 * redisson操作锁
 *
 * @author yuan
 * @date 2020/01/06
 */
public interface RedissonDistributedLocker {
    RLock lock(String lockKey);

    RLock lock(String lockKey, long timeout);

    RLock lock(String lockKey, TimeUnit unit, long timeout);

    boolean tryLock(String lockKey, TimeUnit unit, long waitTime, long leaseTime);

    void unlock(String lockKey);

    void unlock(RLock lock);
}
