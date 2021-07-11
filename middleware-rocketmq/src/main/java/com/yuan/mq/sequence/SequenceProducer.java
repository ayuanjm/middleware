package com.yuan.mq.sequence;

import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.common.RemotingHelper;

/**
 * Copyright(c) 2018 Sunyur.com, All Rights Reserved.
 * Project: sy
 * Author: yuanjiamin
 * CreateDate: 2020/11/26 2:29 下午
 * Description: https://www.jianshu.com/p/c755e4b35912
 */
public class SequenceProducer {
    public static void main(String[] args) throws Exception {
        //创建一个消息生产者，并设置一个消息生产者组
        DefaultMQProducer producer = new DefaultMQProducer("niwei_producer_group");

        //指定 NameServer 地址
        producer.setNamesrvAddr("localhost:9876");

        //初始化 Producer，整个应用生命周期内只需要初始化一次
        producer.start();

        for (int i = 0; i < 10; i++) {
            //创建一条消息对象，指定其主题、标签和消息内容
            Message msg = new Message(
                    //消息主题名
                    "topic_example_java",
                    //消息标签
                    "sequence",
                    //消息内容
                    ("Hello Java demo RocketMQ " + i).getBytes(RemotingHelper.DEFAULT_CHARSET)
            );
            int orderId = 0;
            //发送消息并返回结果
            //要保证消息的顺序性，在发送消息时，这一组消息必须发送到同一个queue中。（一个broker默认4个queue）。
            //在上面的代码中，orderId表示一个订单号。
            //在send方法中实现了一个选择器。这个选择器的作用就是根据orderId对queue的数量取模，保证同一个orderId的所有消息落到同一个queue上。
            SendResult sendResult = producer.send(msg, (list, message, o) -> {
                Integer id = (Integer) o;
                int index = id % list.size();
                return list.get(index);
            }, orderId);

            System.out.printf("%s%n", sendResult);
        }

        // 一旦生产者实例不再被使用则将其关闭，包括清理资源，关闭网络连接等
        producer.shutdown();
    }

}
