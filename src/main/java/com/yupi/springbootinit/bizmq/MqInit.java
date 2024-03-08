package com.yupi.springbootinit.bizmq;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

/**
 * 用于创建测试程序用到的交换机和队列 （在程序启动前只用执行一次）
 */
public class MqInit {
    public static void main(String[] args) {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        try {
            Connection connection = factory.newConnection();
            Channel channel = connection.createChannel();
            String EXCHANGE_NAME = "code_test_exchange";
            channel.exchangeDeclare(EXCHANGE_NAME, "direct");
            //创建一个消息队列
            String queueName1 = "code_queue";
            channel.queueDeclare(queueName1, false, false, false, null);
            channel.queueBind(queueName1, EXCHANGE_NAME, "my_routingKey");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
