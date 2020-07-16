package com.yuan.middleware.jdk.mybatis.bind;

import com.yuan.middleware.jdk.mybatis.entity.SmsMapperXml;
import com.yuan.middleware.jdk.mybatis.session.SqlSession;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * 实现动态代理 生成SmsMapper接口的impl实体类（代理类）（读取SmsMapperXml的内容实现接口对应方法）
 *
 * @author yjm
 * @version 1.0
 * @date 2020/2/28 3:39 下午
 */
public class MapperProxy implements InvocationHandler {
    private SqlSession sqlSession;

    public MapperProxy(SqlSession sqlSession) {
        this.sqlSession = sqlSession;
    }

    /**
     *
     * @param proxy 产生的代理对象  实现smsMapper接口的对象
     * @param method 方法 selectSmsById
     * @param args 参数数组 {1}
     * @return
     * @throws Throwable
     */
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        if (method.getDeclaringClass().getName().equals(SmsMapperXml.nameSpace)) {
            String sql = SmsMapperXml.map.get(method.getName());
            return sqlSession.selectOne(sql, args[0]);
        }
        return null;
    }
}
