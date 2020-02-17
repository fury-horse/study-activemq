package com.msb.topic;


import com.msb.MQFactory;

import javax.jms.*;

/**
* @desc    发布订阅-生产者
* @version 1.0
* @author  Liang Jun
* @date    2020年02月17日 13:43:55
**/
public class TopicProducer {
    public static void main(String[] args) throws Exception {
        //1.创建连接
        Connection conn = MQFactory.getConnect();
        conn.start();
        System.out.println("建立连接成功");
        //2.创建会话
        Session session = conn.createSession(false, Session.AUTO_ACKNOWLEDGE);
        //3.创建目标
        Destination dest = session.createTopic("myTopic");
        //4.创建生产者
        MessageProducer producer = session.createProducer(dest);
        //5.创建消息
        MapMessage mapMessage = session.createMapMessage();
        mapMessage.setString("content", "hello, every one!");
        //6.发送消息
        producer.send(mapMessage);
        //7.关闭连接
        conn.close();
        System.out.println("连接关闭.");
    }
}