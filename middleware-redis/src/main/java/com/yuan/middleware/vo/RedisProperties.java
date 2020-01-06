package com.yuan.middleware.vo;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author yuan
 * @date 2020/01/06
 */

/**
 * 默认的属性值会被配置文件中存在的值覆盖
 *
 * @author yuan
 * @date 2020/01/06
 */
@Data
@Component
@ConfigurationProperties(prefix = "spring.redis")
public class RedisProperties {
    private int database = 0;
    private String url;
    private String host = "localhost";
    private String password;
    private int port = 6379;
    private boolean ssl;
}
