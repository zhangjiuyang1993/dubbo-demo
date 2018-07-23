package com.zjy.activemq;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

/**
 * @author zhangjiuyang
 * @create 2018/7/23
 * @since 1.0.0
 */
public class QueueReceiver {
    // tcp 地址
    public static final String BROKER_URL = "tcp://localhost:61616";
    // 目标，在ActiveMQ管理员控制台创建 http://localhost:8161/admin/queues.jsp
    public static final String TARGET = "sagedragon.mq.queue";

    public static void run() throws Exception {
        QueueConnection connection = null;
        QueueSession session = null;
        try {
            QueueConnectionFactory factory = new ActiveMQConnectionFactory(ActiveMQConnection.DEFAULT_USER, ActiveMQConnection
                    .DEFAULT_PASSWORD, BROKER_URL);
            connection = factory.createQueueConnection();
            connection.start();
            session = connection.createQueueSession(Boolean.TRUE, Session.AUTO_ACKNOWLEDGE);
            Queue queue = session.createQueue(TARGET);
            javax.jms.QueueReceiver receiver = session.createReceiver(queue);
            receiver.setMessageListener(new MessageListener() {
                @Override
                public void onMessage(Message message) {
                    if (message != null) {
                        MapMessage map = (MapMessage) message;
                        try {
                            System.out.println(map.getLong("time") + "接收#" + map.getString("text"));
                        } catch (JMSException e) {
                            e.printStackTrace();
                        }
                    }
                }
            });
            Thread.sleep(1000 * 100);
            session.commit();
        } catch (Exception e) {
            throw e;
        } finally {
            if (session != null) {
                session.close();
            }
            if (connection != null) {
                connection.close();
            }
        }
    }

    public static void main(String[] args) throws Exception{
        QueueReceiver.run();
    }
}
