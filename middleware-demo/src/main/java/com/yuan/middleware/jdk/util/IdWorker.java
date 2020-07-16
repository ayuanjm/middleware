package com.yuan.middleware.jdk.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author yuanjm
 * @date 2020/7/13 6:06 下午
 */
@Slf4j
@Component
public class IdWorker {
    private SnowflakeIdWorker snowflakeIdWorker;

    public IdWorker(@Value("${idworder.worker-id:1}") long worderId, @Value("${idworder.datacenter-id:1}") long detacenterId) {
        log.info("worderId:{},detacenterId:{}", worderId, detacenterId);
        this.snowflakeIdWorker = new SnowflakeIdWorker(worderId, detacenterId);
    }

    public String nextStringId() {
        return String.valueOf(snowflakeIdWorker.nextId());
    }

    public long nextLongId() {
        return snowflakeIdWorker.nextId();
    }

}
