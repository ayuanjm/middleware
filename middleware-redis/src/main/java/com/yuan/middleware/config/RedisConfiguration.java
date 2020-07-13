package com.yuan.middleware.config;

import com.yuan.middleware.vo.RedisProperties;
import lombok.extern.slf4j.Slf4j;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.redisson.config.SingleServerConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import redis.clients.jedis.JedisPoolConfig;

import javax.annotation.Resource;

/**
 * redis配置
 *
 * @author yuan
 * @date 2020/01/06
 */
@Slf4j
@Configuration
public class RedisConfiguration {
    @Resource
    private RedisProperties redssionProperties;

    @Bean
    public RedisTemplate redisTemplate(RedisConnectionFactory redisConnectionFactory) {
        log.info("自定义bean初始化");
        StringRedisTemplate redisTemplate = new StringRedisTemplate();
        redisTemplate.setConnectionFactory(redisConnectionFactory);
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(new Jackson2JsonRedisSerializer(Object.class));
        redisTemplate.setDefaultSerializer(new StringRedisSerializer());
        return redisTemplate;
    }

    /**
     * 连接redis的工厂类
     *
     * @return
     */
    @Bean
    public JedisConnectionFactory jedisConnectionFactory() {
        JedisConnectionFactory factory = new JedisConnectionFactory();
        factory.setHostName(redssionProperties.getHost());
        factory.setPort(redssionProperties.getPort());
        factory.setPassword(redssionProperties.getPassword());
        factory.setDatabase(redssionProperties.getDatabase());
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        jedisPoolConfig.setTestOnBorrow(true);
        jedisPoolConfig.setTestOnReturn(true);
        jedisPoolConfig.setBlockWhenExhausted(false);
        factory.setPoolConfig(jedisPoolConfig);
        return factory;
    }

    /**
     * redisson单机模式自动装配
     *
     * @return
     */
    @Bean
    public RedissonClient redissonSingle() {
        Config config = new Config();
        SingleServerConfig serverConfig = config.useSingleServer()
                .setAddress("redis://" + redssionProperties.getHost() + ":" + redssionProperties.getPort());
        if (notNull(redssionProperties.getPassword())) {
            serverConfig.setPassword(redssionProperties.getPassword());
        }
        return Redisson.create(config);
    }

    public static boolean notNull(Object object) {
        if (object instanceof String) {
            if ("".equals(object) || null == object) {
                return false;
            }
        }
        if (object == null) {
            return false;
        }
        return true;
    }

    public static void main(String[] args) {
        boolean b = notNull(1);
        System.out.println(b);
    }

}
