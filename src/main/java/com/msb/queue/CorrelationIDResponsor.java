package com.msb.queue;


import com.msb.MQFactory;

import javax.jms.*;

/**
* @desc    
* @version 1.0
* @author  Liang Jun
* @date    2020年02月24日 22:55:09
**/
public class CorrelationIDResponsor {
    public static void main(String[] args) throws JMSException {
        Connection connection = MQFactory.getConnect();
        //3.启动连接
        connection.start();
        //4.创建session
        final Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        //5.创建目的地
        Destination destination = session.createQueue("myQueue");
        //6.创建消费者
        MessageConsumer consumer = session.createConsumer(destination);
        consumer.setMessageListener(new MessageListener() {
            @Override
            public void onMessage(Message message) {
                try {
                    System.out.println("**收到消息：" + ((TextMessage)message).getText());

                    Destination ackQueue = session.createQueue("ackQueue");
                    MessageProducer producer = session.createProducer(ackQueue);
                    Message ackMsg = session.createTextMessage("hello, Request");
                    ackMsg.setJMSCorrelationID(message.getJMSCorrelationID());
                    producer.send(ackMsg);
                } catch (JMSException e) {
                    e.printStackTrace();
                }
            }
        });

        //8.关闭连接
//        connection.close();
        System.out.println("connect close.");
    }
}