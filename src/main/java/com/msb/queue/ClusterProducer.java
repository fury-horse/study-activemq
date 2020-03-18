package com.msb.queue;


import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;
import java.io.IOException;

/**
* @desc    
* @version 1.0
* @author  Liang Jun
* @date    2020年02月08日 23:01:08
**/
public class ClusterProducer {
    final private static String url = "failover:(nio://49.235.216.115:61617,nio://49.235.216.115:61618)?Randomize=true";
    public static void main(String[] args) throws JMSException, InterruptedException, IOException {
        //1.获取连接工厂
        ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory(
                ActiveMQConnectionFactory.DEFAULT_USER, ActiveMQConnectionFactory.DEFAULT_PASSWORD, url);
        //2.获取连接
        Connection connection = factory.createConnection();
        connection.start(); //生产者可以不调用start
        //3.获取session
        Session session = connection.createSession(false, Session.CLIENT_ACKNOWLEDGE);
        //4.获取目的地
        Destination destination = session.createQueue("myQueue");
        //5.创建生产者
        MessageProducer producer = session.createProducer(destination);
        for (int i=1; i<=10; i++) {
            //6.创建消息
            TextMessage message = session.createTextMessage("hello, active mq " + i);
            //7.生产者发消息
            producer.send(message);
            System.out.println("发送消息：" + i);
        }

        //8.关闭连接
        //connection.close();
        System.out.println("connect close.");
        System.in.read();
    }
}