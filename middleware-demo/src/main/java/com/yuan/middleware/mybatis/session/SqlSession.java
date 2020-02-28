package com.yuan.middleware.mybatis.session;

import com.yuan.middleware.mybatis.bind.MapperProxy;
import com.yuan.middleware.mybatis.executor.Executor;
import com.yuan.middleware.mybatis.executor.impl.ExecutorImpl;
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
         * InvocationHandler h
         */
        return (T) Proxy.newProxyInstance(clazz.getClassLoader(),
                new Class[]{clazz}, new MapperProxy<>(this));
    }
}
