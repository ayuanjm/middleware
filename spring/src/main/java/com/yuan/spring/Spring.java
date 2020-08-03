package com.yuan.spring;

import com.yuan.spring.dynamic.DynamicDataSourceService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author yuan
 */
public class Spring {
    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
        DynamicDataSourceService bean = context.getBean(DynamicDataSourceService.class);
        bean.jdbcTemplateDynamicDataSource();
    }
}
