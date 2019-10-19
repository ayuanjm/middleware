package com.yuan.middleware.work.consumer;

import com.rabbitmq.client.*;
import com.yuan.middleware.util.RabbitMqConnection;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @author yuan
 */
@Slf4j
public class ConsumerB {
    public static final String QUEUE_NAME = "test_simple_queue";

    public static void main(String[] args) throws IOException, TimeoutException {
        //获取rabbitmq连接
        Connection connection = RabbitMqConnection.getConnection();
        //建立通道
        Channel channel = connection.createChannel();
        //声明队列
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        //定义一个消费者
        DefaultConsumer defaultConsumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                String msg = new String(body, "utf-8");
                log.info("consumer msg:" + msg);
            }
        };
        //监听队列
        channel.basicConsume(QUEUE_NAME, true, defaultConsumer);
    }
}
