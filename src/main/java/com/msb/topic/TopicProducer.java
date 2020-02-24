package com.msb.topic;


import com.msb.MQFactory;
import org.apache.activemq.ScheduledMessage;

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
        //5.1设置消息延迟发送
        mapMessage.setLongProperty(ScheduledMessage.AMQ_SCHEDULED_DELAY, 10 * 1000);
        //5.2设置消息重复发送
        mapMessage.setIntProperty(ScheduledMessage.AMQ_SCHEDULED_REPEAT, 9);
        //5.3设置消息间隔发送
        mapMessage.setLongProperty(ScheduledMessage.AMQ_SCHEDULED_PERIOD, 2 * 1000);
        //5.4设置消息定时发送
        mapMessage.setStringProperty(ScheduledMessage.AMQ_SCHEDULED_CRON, "0 0 12 * * ?"); //每天中午12点触发

        //6.发送消息
        producer.send(mapMessage);
        //7.关闭连接
        conn.close();
        System.out.println("连接关闭.");
    }
}