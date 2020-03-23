package com.yuan.middleware.config;


import com.yuan.middleware.springboot.DirectReceiver;
import org.springframework.amqp.core.AcknowledgeMode;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;

/**
 * 消费者手动确认配置
 *
 * @author yjm
 * @date 2020/3/23 7:21 下午
 */
@Configuration
public class MessageListenerConfig {

    @Resource
    private CachingConnectionFactory connectionFactory;
    /**
     * Direct消息接收处理类
     */
    @Resource
    private DirectReceiver directReceiver;
    @Resource
    DirectRabbitConfig directRabbitConfig;

    @Bean
    public SimpleMessageListenerContainer simpleMessageListenerContainer() {
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer(connectionFactory);
        container.setConcurrentConsumers(1);
        container.setMaxConcurrentConsumers(1);
        // RabbitMQ默认是自动确认，这里改为手动确认消息
        container.setAcknowledgeMode(AcknowledgeMode.MANUAL);
        container.setQueues(directRabbitConfig.testDirectQueue());
        container.setMessageListener(directReceiver);
        return container;
    }
}


