package com.yuan.spring;

import com.yuan.spring.service.ManagerServiceB;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author yuan
 */
public class Spring {
    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
        ManagerServiceB bean = context.getBean(ManagerServiceB.class);
        bean.getUser();
    }
}
