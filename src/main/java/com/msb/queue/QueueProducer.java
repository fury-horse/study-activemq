package com.msb.queue;


import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

/**
* @desc    
* @version 1.0
* @author  Liang Jun
* @date    2020年02月08日 23:01:08
**/
public class QueueProducer {
    final private static String url = "tcp://49.235.216.115:61616";
    public static void main(String[] args) throws JMSException, InterruptedException {
        //1.获取连接工厂
        ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory(
                ActiveMQConnectionFactory.DEFAULT_USER, ActiveMQConnectionFactory.DEFAULT_PASSWORD, url);
        //2.获取连接
        Connection connection = factory.createConnection();
        connection.start();
        //3.获取session
        Session session = connection.createSession(false, Session.CLIENT_ACKNOWLEDGE);
        //4.获取目的地
        Destination destination = session.createQueue("myQueue");
        //5.创建生产者
        MessageProducer producer = session.createProducer(destination);
        for (int i=1; i<=10; i++) {
            //6.创建消息
            TextMessage message = session.createTextMessage("hello, active mq " + i);

            //设置单条消息优先级（0-9越大越先，默认4）
            //message.setJMSPriority(9);
            //设置全部消息优先级（0-9越大越先，默认4）
            //producer.setPriority(9);
            //设置单条消息非持久化（activemq-client:5.15.11未实现）
            //message.setJMSDeliveryMode(DeliveryMode.NON_PERSISTENT);
            //设置全部消息非持久化
            //producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
            //设置全部消息过期时间
            //producer.setTimeToLive(500);
            //设置消息属性（消费者可以做消息过滤）
            message.setIntProperty("age", i);

            //7.生产者发消息
            producer.send(message);
            System.out.println("发送消息：" + i);
            //Thread.sleep(1000);
        }

        //8.关闭连接
        producer.close();
        System.out.println("connect close.");
        System.exit(0);
    }
}