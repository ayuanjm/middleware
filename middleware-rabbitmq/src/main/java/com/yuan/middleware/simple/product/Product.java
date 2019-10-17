package com.yuan.middleware.simple.product;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.yuan.middleware.util.RabbitMqConnection;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * simple 队列是一一对应的，而我们实际开发中，生产者发送消息是毫不费力的，
 * 而消费者一般是要和业务相结合的，消费者接收到消息后就需要处理，可能需要花费时间，
 * 这时候队列就会积压很多消息
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
        String msg = "hello simple !";
        channel.basicPublish("", QUEUE_NAME, null, msg.getBytes());
        log.info("product msg:" + msg);
        channel.close();
        connection.close();
    }
}
