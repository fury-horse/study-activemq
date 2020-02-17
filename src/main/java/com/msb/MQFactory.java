package com.msb;


import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.Connection;
import javax.jms.JMSException;

/**
* @desc    消息中间件连接工厂
* @version 1.0
* @author  Liang Jun
* @date    2020年02月17日 11:44:48
**/
public class MQFactory {
    final private static String url = "tcp://49.235.216.115:61616";
    final private static ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory(
            ActiveMQConnectionFactory.DEFAULT_USER, ActiveMQConnectionFactory.DEFAULT_PASSWORD, url);

    private MQFactory(){}

    public static Connection getConnect() {
        try {
            Connection connection = factory.createConnection();
            return connection;
        } catch (JMSException e) {
            throw new RuntimeException("MQFactory.getConnect 获取连接失败", e);
        }
    }
}