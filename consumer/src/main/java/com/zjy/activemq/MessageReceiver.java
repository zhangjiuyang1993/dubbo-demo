package com.zjy.activemq;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

/**
 * @author zhangjiuyang
 * @create 2018/7/23
 * @since 1.0.0
 */
public class MessageReceiver {
    // tcp 地址
    public static final String BROKER_URL = "tcp://localhost:61616";
    // 目标，在ActiveMQ管理员控制台创建 http://localhost:8161/admin/queues.jsp
    public static final String DESTINATION = "sagedragon.mq.queue";

    public static void run() throws Exception {
        Connection connection = null;
        Session session = null;
        try {
            ConnectionFactory factory = new ActiveMQConnectionFactory(ActiveMQConnection.DEFAULT_USER, ActiveMQConnection
                    .DEFAULT_PASSWORD, BROKER_URL);
            connection = factory.createConnection();
            connection.start();
            session = connection.createSession(Boolean.TRUE, Session.AUTO_ACKNOWLEDGE);
            Destination destination = session.createQueue(DESTINATION);
            MessageConsumer consumer = session.createConsumer(destination);
            while (true) {
                //接收数据的时间
                Message message = consumer.receive(1000 * 100);
                TextMessage text = (TextMessage) message;
                if (text != null) {
                    System.out.println("接收： " + text.getText());
                } else {
                    break;
                }
            }
            //提交会话
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
        MessageReceiver.run();
    }
}
