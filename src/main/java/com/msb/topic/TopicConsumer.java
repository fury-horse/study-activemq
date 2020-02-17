package com.msb.topic;


import com.msb.MQFactory;

import javax.jms.*;

/**
* @desc    发布订阅-消费者
* @version 1.0
* @author  Liang Jun
* @date    2020年02月17日 13:44:26
**/
public class TopicConsumer {
    public static void main(String[] args) throws JMSException {
        //1.创建连接
        Connection conn = MQFactory.getConnect();
        conn.start();
        System.out.println("建立连接成功");
        //2.创建会话
        Session session = conn.createSession(false, Session.AUTO_ACKNOWLEDGE);
        //3.创建目标
        Destination dest = session.createTopic("myTopic");
        //4.创建消费者
        MessageConsumer consumer = session.createConsumer(dest);
        //5.接收消息
        MapMessage message = (MapMessage)consumer.receive();

        System.out.println("收到消息：" + message.getString("content"));
        //6.关闭连接
        conn.close();
        System.out.println("连接关闭.");
    }
}