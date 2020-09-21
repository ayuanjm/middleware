package com.yuan.middleware.direct.consumer;

import com.rabbitmq.client.*;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import static com.yuan.middleware.util.RabbitMqConnection.getConnection;

/**
 * @author yuan
 */
@Slf4j
public class ConsumerB {
    public static void main(String[] args) throws IOException, TimeoutException {
        Connection connection = getConnection();
        Channel channel = connection.createChannel();
        String queueName = "test_queue_direct_b";
        // 声明队列
        /**
         * 这一步声明的队列必须跟我们在生产者中声明的队列一样，参数也必须一样，否则会报错
         * 因为RabbitMQ不允许你重新定义一个已经存在的消息队列，
         * 如果你尝试着去修改它的某些属性的话，那么你的程序将会报错
         * ##### 对队列的声明是幂等的，之所以在这里再次申明是为了方便启动服务
         * 有时候，生产者还没启动，我们消费者已经启动了
         */
        channel.queueDeclare(queueName, true, false, false, null);
        DefaultConsumer defaultConsumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                super.handleDelivery(consumerTag, envelope, properties, body);
                String msg = new String(body, "utf-8");
                log.info("consumer msg:" + msg);
            }
        };
        channel.basicConsume(queueName, true, defaultConsumer);
        log.info("消费端启动成功");
    }
}
