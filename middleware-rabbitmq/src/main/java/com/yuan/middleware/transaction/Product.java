package com.yuan.middleware.transaction;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.yuan.middleware.util.RabbitMqConnection;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * transaction模式 是确认有没有发送到rabbitmq服务器，不是确认消费者有没有消费。
 * 生产者将消息发送出去后到底有没有到达rabbitmq服务器
 * AMQP事物机制 txSelect:将当前channel设置成transaction模式  txCommit:提交事物 txRollback:回滚事物
 *
 * @author yuan
 */
@Slf4j
public class Product {
    public static final String QUEUE_NAME = "test_transaction_queue";

    public static void main(String[] args) throws IOException, TimeoutException {
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
        String msg = "hello transaction message";
        try {
            channel.txSelect();
            channel.basicPublish("", QUEUE_NAME, null, msg.getBytes());
            log.info("send msg:" + msg);
            //出现异常没有发送到rabbitmq服务器
            int a = 1 / 0;
            //必须提交后才能到达rabbitmq服务器
            channel.txCommit();
        } catch (Exception e) {
            e.printStackTrace();
            log.info("send message rollback");
            channel.txRollback();
        }
        channel.close();
        connection.close();
    }
}
