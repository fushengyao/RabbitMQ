package com.rabbitmq.server;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class RabbitMQCustomer {

    public static final String QUEUE_NAME = "rabbitMQ queue";

    public static void main(String[] args) throws Exception {
        //连接操作
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setUsername("root");
        connectionFactory.setPassword("123456");
        connectionFactory.setHost("120.27.211.41");
        connectionFactory.setPort(5672);
        Connection connection = connectionFactory.newConnection();
        final Channel channel = connection.createChannel();
        channel.basicConsume(QUEUE_NAME, new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                System.out.println("能接受到消息了" + new String(body));
                channel.basicAck(envelope.getDeliveryTag(), false);
            }
        });
        TimeUnit.SECONDS.sleep(1);
        channel.close();
        connection.close();;
    }
}
