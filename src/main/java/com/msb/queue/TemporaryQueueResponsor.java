package com.msb.queue;


import com.msb.MQFactory;

import javax.jms.*;

/**
* @desc    
* @version 1.0
* @author  Liang Jun
* @date    2020年02月22日 21:00:41
**/
public class TemporaryQueueResponsor {
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

                    Destination jmsReplyTo = message.getJMSReplyTo();
                    MessageProducer producer = session.createProducer(jmsReplyTo);
                    producer.send(session.createTextMessage("hello, Request"));
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