package com.yuan.middleware.confirm;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.yuan.middleware.util.RabbitMqConnection;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * confirm模式 是确认有没有发送到rabbitmq服务器，不是确认消费者有没有消费。
 *
 * @author yuan
 */
@Slf4j
public class Product {
    public static final String QUEUE_NAME = "test_confirm_queue";

    public static void main(String[] args) throws IOException, TimeoutException, InterruptedException {
        Connection connection = RabbitMqConnection.getConnection();
        Channel channel = connection.createChannel();
        /**
         * @params 参数列表：
         * String queue: 声明的队列名称
         * boolean durable：消息是否持久化，若设置为true，则MQ重启后，队列仍然存在
         * boolean exclusive：是否独占连接，设置为true,连接关闭则队列被删除，一般用于临时队列的创建，跟autoDelete配合使用
         * boolean autoDelete：是否自动删除，设置为true,则队列不使用就自动删除，一般用于临时队列的创建
         * Map<String, Object> arguments：设置队列的一些扩展参数
         */
        channel.queueDeclare(QUEUE_NAME, true, false, false, null);

        //生产者调用confirmSelect 将channel设置为confirm模式
        channel.confirmSelect();

        String msg = "hello confirm message";
        for (int i = 0; i < 10; i++) {
            channel.basicPublish("", QUEUE_NAME, null, msg.getBytes());
        }

        //同步模式会阻塞方法，直到mq服务器确认消息，调用waitForConfirms()确认，根据返回值判断是否发送到rabbitmq，
        //只是确认功能，可以根据确认结果做相对应处理，可以没有这一步不影响消费者消费。
        if (channel.waitForConfirms()) {
            log.info("send msg ok");
        } else {
            log.info("send msg failed");
        }
        channel.close();
        connection.close();
    }
}
