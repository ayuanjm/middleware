package com.yuan.mq.sequence;

import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeOrderlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerOrderly;
import org.apache.rocketmq.common.consumer.ConsumeFromWhere;
import org.apache.rocketmq.common.message.MessageExt;

/**
 * Copyright(c) 2018 Sunyur.com, All Rights Reserved.
 * Project: sy
 * Author: yuanjiamin
 * CreateDate: 2020/11/26 3:05 下午
 * Description:
 */
public class SequenceConsumer {
    public static void main(String[] args) throws Exception {
        //创建一个消息消费者，并设置一个消息消费者组
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("niwei_consumer_group");
        //指定 NameServer 地址
        consumer.setNamesrvAddr("localhost:9876");
        //这里设置的是一个consumer的消费策略
        //CONSUME_FROM_LAST_OFFSET 默认策略，从该队列最尾开始消费，即跳过历史消息
        //CONSUME_FROM_FIRST_OFFSET 从队列最开始开始消费，即历史消息（还储存在broker的）全部消费一遍
        //CONSUME_FROM_TIMESTAMP 从某个时间点开始消费，和setConsumeTimestamp()配合使用，默认是半个小时以前
        //设置 Consumer 第一次启动时从队列头部开始消费还是队列尾部开始消费
        consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_FIRST_OFFSET);
        //订阅指定 Topic 下的所有消息
        consumer.subscribe("topic_example_java", "sequence");
        //注册消息监听器
        //保证了生产端的消息顺序性，那么消费端必须保证消息被顺序的消费。使用MessageListenerOrderly。作用是，必须等前面的消息消费完，后面的消息才能进行消费。
        consumer.registerMessageListener((MessageListenerOrderly) (list, context) -> {
            context.setAutoCommit(true);
            for (MessageExt ext : list) {
                try {
                    System.out.println(Thread.currentThread().getName() + " " + new String(ext.getBody(), "UTF-8"));
//                    TimeUnit.SECONDS.sleep(1);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            //返回消费状态
            //CONSUME_SUCCESS 消费成功
            //RECONSUME_LATER 消费失败，需要稍后重新消费
            return ConsumeOrderlyStatus.SUCCESS;
        });

        // 消费者对象在使用之前必须要调用 start 初始化
        consumer.start();
        //查看输出，并不是单线程处理的，但是保证了顺序消费。
        System.out.println("消息消费者已启动");
    }
}
