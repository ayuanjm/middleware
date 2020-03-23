package com.yuan.middleware.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


/**
 * 队列 交换机 绑定 配置
 *
 * @author yjm
 * @date 2020/3/23 5:59 下午
 */
@Configuration
public class DirectRabbitConfig {
    /**
     * 队列 起名：TestDirectQueue
     */
    @Bean
    public Queue testDirectQueue() {
        //true 是否持久
        return new Queue("TestDirectQueue", true);
    }

    /**
     * Direct交换机 起名：TestDirectExchange
     */
    @Bean
    DirectExchange testDirectExchange() {
        return new DirectExchange("TestDirectExchange");
    }

    /**
     * 绑定  将队列和交换机绑定, 并设置用于匹配键：TestDirectRouting
     */
    @Bean
    Binding bindingDirect() {
        return BindingBuilder.bind(testDirectQueue()).to(testDirectExchange()).with("TestDirectRouting");
    }

}
