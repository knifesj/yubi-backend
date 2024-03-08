package com.yupi.springbootinit.mq;

import com.rabbitmq.client.*;

public class DirectConsumer {

  private static final String EXCHANGE_NAME = "direct_exchange";

  public static void main(String[] argv) throws Exception {
    ConnectionFactory factory = new ConnectionFactory();
    factory.setHost("localhost");
    Connection connection = factory.newConnection();
    Channel channel = connection.createChannel();

    channel.exchangeDeclare(EXCHANGE_NAME, "direct");
    //创建一个消息队列
      String queueName1 = "xiaoyu_Queue";
      channel.queueDeclare(queueName1,false,false,false,null);
      channel.queueBind(queueName1, EXCHANGE_NAME, "xiaoyu");

      String queueName2 = "xiaopi_Queue";
      channel.queueDeclare(queueName2,false,false,false,null);
      channel.queueBind(queueName2, EXCHANGE_NAME, "xiaopi");

      System.out.println(" [*] Waiting for messages. To exit press CTRL+C");

        DeliverCallback deliverCallback1 = (consumerTag, delivery) -> {
            String message = new String(delivery.getBody(), "UTF-8");
            System.out.println(" [小鱼] Received '" +
                delivery.getEnvelope().getRoutingKey() + "':'" + message + "'");
        };
      DeliverCallback deliverCallback2= (consumerTag, delivery) -> {
          String message = new String(delivery.getBody(), "UTF-8");
          System.out.println(" [小皮] Received '" +
                  delivery.getEnvelope().getRoutingKey() + "':'" + message + "'");
      };
      channel.basicConsume(queueName1, true, deliverCallback1, consumerTag -> { });
      channel.basicConsume(queueName2, true, deliverCallback2, consumerTag -> { });
  }
}