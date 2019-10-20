package com.yuan.middleware.confirm;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.ConfirmListener;
import com.rabbitmq.client.Connection;
import com.yuan.middleware.util.RabbitMqConnection;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.Collections;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.concurrent.TimeoutException;

/**
 * confirm模式 是确认有没有发送到rabbitmq服务器，不是确认消费者有没有消费。
 * 普通confirm模式：每发送一条消息后，调用waitForConfirms()方法，等待服务器端confirm。实际上是一种串行confirm了。
 * 批量confirm模式：每发送一批消息后，调用waitForConfirms()方法，等待服务器端confirm。
 * 异步confirm模式：提供一个回调方法，服务端confirm了一条或者多条消息后Client端会回调这个方法。
 *
 * @author yuan
 */
@Slf4j
public class ProductAsy {
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
        //未确认的消息标识
        SortedSet<Long> confirmSet = Collections.synchronizedSortedSet(new TreeSet<>());
        channel.addConfirmListener(new ConfirmListener() {
            /**
             * 消息成功处理
             * @param deliveryTag 唯一消息标签
             * @param multiple 是否批量
             * @throws IOException
             */
            @Override
            public void handleAck(long deliveryTag, boolean multiple) throws IOException {
//                log.info("msg ack");
                //判断是否是批量
                if (multiple) {
                    log.info("handleAck multiple:" + deliveryTag);
                    //批量删除当前元素及之前的，headSet：取当前元素之前的数据，+1把当前元素也取到
                    confirmSet.headSet(deliveryTag + 1).clear();
                } else {
                    log.info("handleAck multiple false:" + deliveryTag);
                    //不是批量只删除当前元素
                    confirmSet.remove(deliveryTag);
                }
            }

            //消息失败处理
            @Override
            public void handleNack(long deliveryTag, boolean multiple) throws IOException {
//                log.info("msg nack");
                //判断是否是批量
                if (multiple) {
                    log.info("handleNack multiple:" + deliveryTag);
                    //批量删除当前元素及之前的，headSet：取当前元素之前的数据，+1把当前元素也取到
                    confirmSet.headSet(deliveryTag + 1).clear();
                } else {
                    log.info("handleNack multiple false:" + deliveryTag);
                    //不是批量只删除当前元素
                    confirmSet.remove(deliveryTag);
                }
            }
        });
        String msg = "hello asynchronous message";

        while (true) {
            long seqNo = channel.getNextPublishSeqNo();
            log.info("seqNo:" + seqNo + ",msg:" + msg);
            channel.basicPublish("", QUEUE_NAME, null, msg.getBytes());
            confirmSet.add(seqNo);
        }
    }
}
