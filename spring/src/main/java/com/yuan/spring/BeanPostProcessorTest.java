package com.yuan.spring;

import lombok.Data;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

/**
 * 测试BeanPostProcessor
 *
 * @author yuan
 */
@Data
//@Service
public class BeanPostProcessorTest implements BeanPostProcessor {

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        System.out.println("before:" + beanName);
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        System.out.println("after:" + beanName);
        return bean;
    }

    public static void main(String[] args) {
        ClassPathResource resource = new ClassPathResource("applicationContext.xml");
        DefaultListableBeanFactory factory = new DefaultListableBeanFactory();
        BeanPostProcessorTest spring = new BeanPostProcessorTest();
        // BeanFactory 容器一定要调用该方法进行 BeanPostProcessor 注册
        factory.addBeanPostProcessor(spring);
        XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(factory);
        reader.loadBeanDefinitions(resource);
        System.out.println("开始加载bean");
        factory.getBean("beanPostProcessorTest");
    }
}
