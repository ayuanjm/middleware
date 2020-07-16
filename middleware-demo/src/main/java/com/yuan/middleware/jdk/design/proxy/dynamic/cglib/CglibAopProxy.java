package com.yuan.middleware.jdk.design.proxy.dynamic.cglib;

import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * @author yjm
 * @date 2020/3/20 7:21 下午
 */
public class CglibAopProxy implements MethodInterceptor {
    @Override
    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
        before();
        //特别注意是invokeSuper不是invoke
        methodProxy.invokeSuper(o, objects);
        after();
        return null;
    }


    public void before() {
        System.out.println("before");
    }

    public void after() {
        System.out.println("after");
    }

    public static void main(String[] args) {
        //增强器
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(CglibHello.class);
        enhancer.setCallback(new CglibAopProxy());
        CglibHello cglibHello = (CglibHello) enhancer.create();
        cglibHello.say("hello cglib");

    }
}
