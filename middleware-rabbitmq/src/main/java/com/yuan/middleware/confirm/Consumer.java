package com.yuan.middleware.confirm;

import com.rabbitmq.client.*;
import com.yuan.middleware.util.RabbitMqConnection;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @author yuan
 */
@Slf4j
public class Consumer {
    public static final String QUEUE_NAME = "test_confirm_queue";

    public static void main(String[] args) throws IOException, TimeoutException {
        Connection connection = RabbitMqConnection.getConnection();
        Channel channel = connection.createChannel();
        channel.queueDeclare(QUEUE_NAME, true, false, false, null);

        DefaultConsumer defaultConsumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                String msg = new String(body, "utf-8");
                log.info("consumer msg:" + msg);
            }
        };
        channel.basicConsume(QUEUE_NAME, true, defaultConsumer);

    }
}
