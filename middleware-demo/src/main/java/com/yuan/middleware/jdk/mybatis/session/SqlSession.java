package com.yuan.middleware.jdk.mybatis.session;

import com.yuan.middleware.jdk.mybatis.executor.Executor;
import com.yuan.middleware.jdk.mybatis.executor.impl.ExecutorImpl;
import com.yuan.middleware.jdk.mybatis.bind.MapperProxy;
import lombok.Data;

import java.lang.reflect.Proxy;

/**
 * 加载代理对象执行sql
 *
 * @author yjm
 * @version 1.0
 * @date 2020/2/28 3:46 下午
 */
@Data
public class SqlSession {
    private Executor executor = new ExecutorImpl();

    public <T> T selectOne(String statement, Object parameter) {
        return executor.query(statement, parameter);
    }

    public <T> T getProxy(Class<T> clazz) {
        /**
         * ClassLoader loader,
         * Class<?>[] interfaces,代理类要实现的接口列表，这里要实现的接口为 interface com.yuan.middleware.mybatis.mapper.SmsMapper
         * InvocationHandler h  实现了InvocationHandler接口的类，以后调用方法clazz（SmsMapper）接口的方法，实际上都是去调用 h的invoke方法
         */
        return (T) Proxy.newProxyInstance(clazz.getClassLoader(),
                new Class[]{clazz}, new MapperProxy(this));
    }
}
