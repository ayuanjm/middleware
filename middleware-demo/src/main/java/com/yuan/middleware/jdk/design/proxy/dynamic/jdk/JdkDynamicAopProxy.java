package com.yuan.middleware.jdk.design.proxy.dynamic.jdk;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @author yjm
 * @date 2020/3/20 7:01 下午
 */
public class JdkDynamicAopProxy implements InvocationHandler {
    private Object target;

    public JdkDynamicAopProxy(Object target) {
        this.target = target;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws InvocationTargetException, IllegalAccessException {
        before();
        method.invoke(target, args);
        after();
        return null;
    }

    public void before() {
        System.out.println("before");
    }

    public void after() {
        System.out.println("after");
    }

    public <T> T getProxy() {
        return (T) Proxy.newProxyInstance(target.getClass().getClassLoader(), target.getClass().getInterfaces(), this::invoke);
    }

    public static void main(String[] args) {
        JdkDynamicAopProxy jdkDynamicProxy = new JdkDynamicAopProxy(new HelloImpl());
        Hello o = (Hello) Proxy.newProxyInstance(HelloImpl.class.getClassLoader(), HelloImpl.class.getInterfaces(), jdkDynamicProxy);
        o.say("yuan");
        System.out.println("---------------");
        Hello proxy = new JdkDynamicAopProxy(new HelloImpl()).getProxy();
        proxy.say("hello word");

    }
}
