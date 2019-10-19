package com.yuan.middleware.publish.product;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import static com.yuan.middleware.util.RabbitMqConnection.getConnection;

/**
 * publish/subscribe 发布订阅模式
 * 一个生产者多个消费者，每一个消费者都有自己的队列，生产者没有直接把消息发送到队列，
 * 而是发送到了交换机exchange，每个队列都要绑定到交换机上，生产者发送的消息经过交换机到达队列，
 * 就能实现一个消息被多个消费者消费。
 * ​消息始终都是先发送到交换机，由交换机经过路由传送给队列，消费者再从队列中获取消息的！
 *
 * @author yuan
 */
@Slf4j
public class Product {
    public static void main(String[] args) throws IOException, TimeoutException {
        Connection connection = getConnection();
        // 建立通道
        Channel channel = connection.createChannel();
        String queueNameA = "test_queue_fanout_a";
        String queueNameB = "test_queue_fanout_b";

        String exchangeName = "test_exchange_fanout";
        // 声明队列
        /**
         * @params 参数列表：
         * String queue: 声明的队列名称
         * boolean durable：消息是否持久化，若设置为true，则MQ重启后，队列仍然存在
         * boolean exclusive：是否独占连接，设置为true,连接关闭则队列被删除，一般用于临时队列的创建，跟autoDelete配合使用
         * boolean autoDelete：是否自动删除，设置为true,则队列不使用就自动删除，一般用于临时队列的创建
         * Map<String, Object> arguments：设置队列的一些扩展参数
         */
        channel.queueDeclare(queueNameA, true, false, false, null);
        channel.queueDeclare(queueNameB, true, false, false, null);

        // 声明交换机
        /**
         * String exchange, 交换机名称
         * String type,  交换机类型
         * boolean durable  是否持久化
         */
        channel.exchangeDeclare(exchangeName, "fanout", true, false, null);

        // 交换机跟队列绑定,或者说创建了一个binding，
        // 其实就是交换机跟队列的一个绑定关系
        /**
         * String queue,  队列名称
         * String exchange,  交换机名称
         * String routingKey 路由key,路由模式下，我们指定这个key值
         */
        channel.queueBind(queueNameA, exchangeName, "");
        channel.queueBind(queueNameB, exchangeName, "");


        for (int i = 0; i < 5; i++) {
            String msg = "Hello RabbitMQ Message " + i;
            log.info("生产端发送：{}", msg);
            channel.basicPublish(exchangeName, "", null, msg.getBytes());
        }
        connection.close();
    }


}
