package com.yuan.middleware.demo.product;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeoutException;

import static com.yuan.middleware.util.RabbitMqConnection.getConnection;

/**
 * @author yuan
 */
@Slf4j
public class Product {
    public static void main(String[] args) throws IOException, TimeoutException {
        sendMsg();

    }

    private static void sendMsg() throws IOException, TimeoutException {
        Connection connection = getConnection();
        // 建立通道
        Channel channel = connection.createChannel();
        String queueNameA = "test_ack_queue_a";
        String queueNameB = "test_ack_queue_b";

        String exchangeName = "test_ack_exchange";
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
        channel.exchangeDeclare(exchangeName, "topic", true, false, null);

        String routingKey = "ack.save";

        // 交换机跟队列绑定,或者说创建了一个binding，
        // 其实就是交换机跟队列的一个绑定关系
        /**
         * String queue,  队列名称
         * String exchange,  交换机名称
         * String routingKey 路由key,路由模式下，我们指定这个key值
         */
        channel.queueBind(queueNameA, exchangeName, routingKey);
        channel.queueBind(queueNameB, exchangeName, "routingKey");


        for (int i = 0; i < 5; i++) {
            Map<String, Object> headers = new HashMap<>(16);
            headers.put("num", i);
            AMQP.BasicProperties properties = new AMQP.BasicProperties.Builder()
                    .deliveryMode(2)
                    .contentEncoding("UTF-8")
                    .headers(headers)
                    .build();
            String msg = "Hello RabbitMQ ACK Message " + i;
            log.info("生产端发送：{}", msg);
            channel.basicPublish(exchangeName, routingKey, true, properties, msg.getBytes());
        }
        connection.close();
    }


}
