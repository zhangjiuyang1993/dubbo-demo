package com.zjy.activemq;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import org.springframework.jms.core.JmsTemplate;

import java.util.Map;

/**
 * @author zhangjiuyang
 * @create 2018/7/23
 * @since 1.0.0
 */
public class SpringReceiver {
    @SuppressWarnings("unchecked")
    public static void main(String[] args) {
        ApplicationContext ctx = new FileSystemXmlApplicationContext("classpath:applicationContext-*.xml");

        JmsTemplate jmsTemplate = (JmsTemplate) ctx.getBean("jmsTemplate");
        while (true) {
            Map<String, Object> map = (Map<String, Object>) jmsTemplate.receiveAndConvert();

            System.out.println("收到消息：" + map.get("message"));
        }
    }

}
