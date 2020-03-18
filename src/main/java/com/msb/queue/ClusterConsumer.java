package com.msb.queue;


import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

/**
* @desc    
* @version 1.0
* @author  Liang Jun
* @date    2020年02月08日 23:15:28
**/
public class ClusterConsumer {
    final private static String url = "failover:(nio://49.235.216.115:61617,nio://49.235.216.115:61618)?Randomize=true";
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
        Destination destination = session.createQueue("myQueue?consumer.exclusive=true");
        //6.创建消费者
        MessageConsumer consumer = session.createConsumer(destination);

        consumer.setMessageListener(new MessageListener() {
            @Override
            public void onMessage(Message msg) {
                try {
                    TextMessage message = (TextMessage) msg;
                    System.out.println(":" + message.getText());
                } catch (JMSException e) {
                    e.printStackTrace();
                }
            }
        });

        //8.关闭连接
        //connection.close();
        System.out.println("connect close.");
        System.in.read();
    }
}