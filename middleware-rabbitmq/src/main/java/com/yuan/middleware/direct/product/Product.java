package com.yuan.middleware.direct.product;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import static com.yuan.middleware.util.RabbitMqConnection.getConnection;

/**
 * （DIRECT）直连交换机，多个相同队列时还是轮询消费，和工作队列一样。
 *  当队列不一样路由键一样时，同一消息被多个队列消费。
 *  只要队列一样就不会被重复消费，都是轮询消费，不管是哪种交换机。因此在微服务，多个负载的情况下，也不会出现同一消息被多个服务的相同队列消费。
 *
 * @author yuan
 */
@Slf4j
public class Product {
    public static void main(String[] args) throws IOException, TimeoutException {
        Connection connection = getConnection();
        // 建立通道
        Channel channel = connection.createChannel();
        String queueNameA = "test_queue_direct_a";
        String queueNameB = "test_queue_direct_b";

        String exchangeName = "test_exchange_direct";
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
         * String type,  交换机类型，需要根据情况指定类型
         *（FANOUT）扇形交换机，（DIRECT）直连交换机，（TOPIC） 主题交换机，也叫通配符交换机，（HEADER）首部交换机
         *
         * 扇形交换机fanout:不处理路由键(routingKey失效)，会发送到所有与之绑定的队列。
         * 就算发送绑定交换机时指定了routingKey，发送时指定了routingKey,也会送到所有与之绑定的队列，路由失效。
         *
         * boolean durable  是否持久化
         */
        channel.exchangeDeclare(exchangeName, "direct", true, false, null);

        // 交换机跟队列绑定,或者说创建了一个binding，
        // 其实就是交换机跟队列的一个绑定关系
        /**
         * String queue,  队列名称
         * String exchange,  交换机名称
         * String routingKey 路由key,路由模式下，我们指定这个key值
         */
        channel.queueBind(queueNameA, exchangeName, "1");
        channel.queueBind(queueNameB, exchangeName, "1");


        for (int i = 0; i < 5; i++) {
            String msg = "Hello RabbitMQ Message " + i;
            log.info("生产端发送：{}", msg);
            channel.basicPublish(exchangeName, "1", null, msg.getBytes());
        }
        connection.close();
    }


}
