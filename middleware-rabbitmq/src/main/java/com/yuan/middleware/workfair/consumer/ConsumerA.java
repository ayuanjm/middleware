package com.yuan.middleware.workfair.consumer;

import com.rabbitmq.client.*;
import com.yuan.middleware.util.RabbitMqConnection;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @author yuan
 */
@Slf4j
public class ConsumerA {
    public static final String QUEUE_NAME = "test_simple_queue";

    public static void main(String[] args) throws IOException, TimeoutException {
        //获取rabbitmq连接
        Connection connection = RabbitMqConnection.getConnection();
        //建立通道
        Channel channel = connection.createChannel();

        /**
         * 声明队列
         * @params 参数列表：
         * String queue: 声明的队列名称
         * boolean durable：消息是否持久化，若设置为true，则MQ重启后，队列仍然存在
         * boolean exclusive：是否独占连接，设置为true,连接关闭则队列被删除，一般用于临时队列的创建，跟autoDelete配合使用
         * boolean autoDelete：是否自动删除，设置为true,则队列不使用就自动删除，一般用于临时队列的创建
         * Map<String, Object> arguments：设置队列的一些扩展参数
         */
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);

        /**
         * 每个消费者发送确认消息之前，消息队列不再发送下一个消息，一次只处理一个消息
         * 限制发给同一个消费者不得超过一条消息（消费者确认后才会再次发送消息）
         */
        //保证一次只分发一个，只在消费者端起作用，写在生产者端没用。
        channel.basicQos(1);
        //定义一个消费者
        DefaultConsumer defaultConsumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                String msg = new String(body, "utf-8");
                log.info("consumer msg:" + msg);
                /**
                 * 如果注释这行，不给回执也会接收消息，接收的数量取决于channel.basicQos(1);中的参数
                 * 但是只是接收了消息不会消费掉，关闭这个消费者后，消息还在队列中存在，可以再次消费。
                 * 只有给了回执消息才算是消费了,rabbitmq才会删除内存中的消息
                 */
                //手动回执
                channel.basicAck(envelope.getDeliveryTag(), false);
            }
        };

        /**
         * 手工签收 autoAck = false: channel.basicAck(envelope.getDeliveryTag(),false);
         * 自动应答 autoAck = true: 一旦rabbitmq将消息分发给消费者就会从内存中删除,
         * 这种情况下如果杀死正在执行的消费者，就会丢失正在处理的消息
         */
        boolean autoAck = false;
        //监听队列
        channel.basicConsume(QUEUE_NAME, autoAck, defaultConsumer);
    }
}
