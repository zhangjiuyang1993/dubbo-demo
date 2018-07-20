package com.zjy;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author zhangjiuyang
 * @create 2018/7/20
 * @since 1.0.0
 */
public class UserServiceConsumer {
    private static Logger logger = LoggerFactory.getLogger(UserServiceConsumer.class);

    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
        IUserService userService = (IUserService) context.getBean("userService");
        logger.info("执行结果：" + userService.login("hello", "hello"));
    }
}
