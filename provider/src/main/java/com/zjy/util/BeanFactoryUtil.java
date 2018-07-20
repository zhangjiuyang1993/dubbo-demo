package com.zjy.util;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author zhangjiuyang
 * @create 2018/7/20
 * @since 1.0.0
 */
public class BeanFactoryUtil {
    private static ApplicationContext context = null;

    public final static String ApplicationContextRoot = "";
    public final static String ApplicationContextPath = ApplicationContextRoot + "applicationContext.xml";

    public static void init() {
        if (context == null) {
            synchronized (BeanFactoryUtil.class) {
                if (context == null) {
                    String[] configurations = new String[]{ApplicationContextPath};
                    context = new ClassPathXmlApplicationContext(configurations);
                }
            }
        }
    }

    public static ApplicationContext getContext() {
        init();
        return context;
    }
}
