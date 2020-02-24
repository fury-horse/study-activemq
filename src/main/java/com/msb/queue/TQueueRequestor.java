package com.msb.queue;


import com.msb.MQFactory;
import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

/**
* @desc    
* @version 1.0
* @author  Liang Jun
* @date    2020年02月22日 18:34:28
**/
public class TQueueRequestor {
    public static void main(String[] args) throws JMSException {
        ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory(
                ActiveMQConnectionFactory.DEFAULT_USER,
                ActiveMQConnectionFactory.DEFAULT_PASSWORD,
                "tcp://49.235.216.115:61616");


        ActiveMQConnection connect = (ActiveMQConnection) MQFactory.getConnect();
        connect.start(); //生产者可以不调用start
        //3.获取session
        QueueSession session = connect.createQueueSession(false, Session.CLIENT_ACKNOWLEDGE);
        //4.获取目的地
        Queue destination = session.createQueue("myQueue");

        QueueRequestor requestor = new QueueRequestor(session, destination);
        System.out.println("==开始发送请求");
        Message message = requestor.request(session.createTextMessage("hello, QueueRequest"));
        System.out.println("==收到响应消息:" + ((TextMessage)message).getText());

        connect.close();
    }
}