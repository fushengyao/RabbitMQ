package com.rabbitmq.server;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.MessageProperties;

public class RabbitMQProductor {

    public static final String EXCHANGE_NAMW = "rabbitMQ exchange";
    public static final String QUEUE_NAME = "rabbitMQ queue";

    public static void main(String[] args) throws Exception {
        //获取连接工厂
        ConnectionFactory connectionFactory = new ConnectionFactory();
        //给连接工厂设置一系列属性
        connectionFactory.setUsername("root");
        connectionFactory.setPassword("123456");
        connectionFactory.setHost("120.27.211.41");
        connectionFactory.setPort(5672);
        //获取连接
        Connection connection = connectionFactory.newConnection();
        //创建信道
        Channel channel = connection.createChannel();
        //创建交换器
        channel.exchangeDeclare(EXCHANGE_NAMW, "direct");
        //创建队列
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        //绑定交换器和队列的路由键
        channel.queueBind(QUEUE_NAME, EXCHANGE_NAMW, "first test");
        //发送消息
        channel.basicPublish(EXCHANGE_NAMW, "first test", MessageProperties.PERSISTENT_TEXT_PLAIN, "hello world".getBytes());
        //关闭资源
        channel.close();
        connection.close();
    }

}
