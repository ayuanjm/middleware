package com.yuan.middleware.util;

import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.yuan.middleware.rabbitmq.config.RabbitMqConfig;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @author yuan
 * 获取rabbitmq连接
 */
public class RabbitMqConnection {
    public static Connection getConnection() throws IOException, TimeoutException {
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setPort(RabbitMqConfig.PORT);
        connectionFactory.setHost(RabbitMqConfig.HOST);
        // 设置虚拟机，一个虚拟机相当于一个独立的mq
        connectionFactory.setVirtualHost(RabbitMqConfig.DEFAULT_VIRTUAL_HOST);
        return connectionFactory.newConnection();
    }
}
