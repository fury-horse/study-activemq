package com.msb.queue;


import com.msb.MQFactory;

import javax.jms.*;
import java.util.UUID;

/**
* @desc    
* @version 1.0
* @author  Liang Jun
* @date    2020年02月24日 22:55:09
**/
public class CorrelationIDRequestor {
    public static void main(String[] args) throws JMSException {
        Connection connection = MQFactory.getConnect();
        //3.启动连接
        connection.start();
        //4.创建session
        final Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

        //5.创建目的地
        Destination destination = session.createQueue("myQueue");
        MessageProducer producer = session.createProducer(destination);
        TextMessage message = session.createTextMessage("你好我是周杰伦");

        String cid = UUID.randomUUID().toString();
        message.setJMSCorrelationID(cid);
        producer.send(message);
        System.out.println("==发送消息成功");

        Destination ackQueue = session.createQueue("ackQueue");
        MessageConsumer consumer = session.createConsumer(ackQueue, "JMSCorrelationID='" + cid + "'");
        consumer.setMessageListener(new MessageListener() {
            @Override
            public void onMessage(Message message) {
                try {
                    System.out.println("==收到消息回复：" + ((TextMessage)message).getText());
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