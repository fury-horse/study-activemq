package com.msb.queue;


import com.msb.MQFactory;

import javax.jms.*;
import java.util.Enumeration;

/**
* @desc    
* @version 1.0
* @author  Liang Jun
* @date    2020年02月22日 18:34:28
**/
public class TQueueBrowser {
    public static void main(String[] args) throws JMSException {
        Connection connect = MQFactory.getConnect();
        connect.start(); //生产者可以不调用start
        //3.获取session
        Session session = connect.createSession(false, Session.CLIENT_ACKNOWLEDGE);
        //4.获取目的地

        QueueBrowser browser = session.createBrowser(session.createQueue("myQueue"));

        Enumeration enumeration = browser.getEnumeration();
        while (enumeration.hasMoreElements()) {
            TextMessage message = (TextMessage) enumeration.nextElement();
            System.out.println(message.getText());
        }

        connect.close();
    }
}