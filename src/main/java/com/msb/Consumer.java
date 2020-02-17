package com.msb;


import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

/**
* @desc    
* @version 1.0
* @author  Liang Jun
* @date    2020年02月08日 23:15:28
**/
public class Consumer {
    final private static String url = "tcp://49.235.216.115:61616";
    public static void main(String[] args) throws Exception {
        //1.获取连接工厂
        ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory(
                ActiveMQConnectionFactory.DEFAULT_USER, ActiveMQConnectionFactory.DEFAULT_PASSWORD, url);
        //2.获取一个连接
        Connection connection = factory.createConnection();
        //3.启动连接
        connection.start();
        //4.创建session
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        //5.创建目的地
        Destination destination = session.createQueue("myQueue");
        //6.创建消费者
        MessageConsumer consumer = session.createConsumer(destination);
        //7.接收消息
        for (int i=1; i>0; i++) {
            TextMessage message = (TextMessage) consumer.receive();
            System.out.println(":" + message.getText());
            if (message.getText().equals("bye bye")) break;
        }
        //8.关闭连接
        connection.close();
        System.out.println("connect close.");
    }
}