package com.zjy.MQ;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQQueue;

import javax.jms.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author zhangjiuyang
 * @create 2018/7/23
 * @since 1.0.0
 */
public class Producer {
    private static final String USERNAME = ActiveMQConnection.DEFAULT_USER;
    private static final String PASSWORD = ActiveMQConnection.DEFAULT_PASSWORD;
    private static final String BROKEN_URL = ActiveMQConnection.DEFAULT_BROKER_URL;
    AtomicInteger count = new AtomicInteger();
    ConnectionFactory connectionFactory;
    Connection connection;
    Session session;
    ThreadLocal<MessageProducer> threadLocal = new ThreadLocal<>();
    public void init() {
        try {
            connectionFactory = new ActiveMQConnectionFactory(USERNAME, PASSWORD, BROKEN_URL);
            connection = connectionFactory.createConnection();
            connection.start();
            session = connection.createSession(true,Session.SESSION_TRANSACTED);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void sendMessage(String disName) {
        try {
            Queue queue = session.createQueue(disName);
            MessageProducer messageProducer = null;
            if (threadLocal.get() != null) {
                messageProducer = threadLocal.get();
            } else {
                messageProducer = session.createProducer(queue);
                threadLocal.set(messageProducer);
            }
            while (true) {
                Thread.sleep(1000);
                int num = count.getAndIncrement();
                //创建一条消息
                TextMessage msg = session.createTextMessage(Thread.currentThread().getName()+
                        "productor:我是大帅哥，我现在正在生产东西！,count:"+num);
                System.out.println(Thread.currentThread().getName()+
                        "productor:我是大帅哥，我现在正在生产东西！,count:"+num);
                //发送消息
                messageProducer.send(msg);
                //提交事务
                session.commit();
            }
        } catch (JMSException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
