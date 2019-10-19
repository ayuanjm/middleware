package com.yuan.middleware.work.product;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.yuan.middleware.util.RabbitMqConnection;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * simple和work的区别在于，一对一和一对多 queue都是相同的
 * @author yuan
 * @date 2019-10-14 22:39
 * 生产者发送消息
 */
@Slf4j
public class Product {
    public static final String QUEUE_NAME = "test_simple_queue";

    public static void main(String[] args) throws IOException, TimeoutException {
        //获取rabbitmq连接
        Connection connection = RabbitMqConnection.getConnection();
        //建立通道
        Channel channel = connection.createChannel();
        //声明队列
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        for (int i = 0; i < 20; i++) {
            String msg = "hello simple:" + i;
            channel.basicPublish("", QUEUE_NAME, null, msg.getBytes());
            log.info("product msg:" + msg);
        }
        channel.close();
        connection.close();
    }
}
