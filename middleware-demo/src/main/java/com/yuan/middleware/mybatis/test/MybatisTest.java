package com.yuan.middleware.mybatis.test;

import com.yuan.middleware.mybatis.entity.Sms;
import com.yuan.middleware.mybatis.mapper.SmsMapper;
import com.yuan.middleware.mybatis.session.SqlSession;

/**
 * 通过sqlSession加载代理对象执行sql
 *
 * @author yjm
 * @version 1.0
 * @date 2020/2/28 6:38 下午
 */
public class MybatisTest {
    public static void main(String[] args) {
        SqlSession sqlSession = new SqlSession();
        //加载SmsMapper的代理对象
        SmsMapper proxy = sqlSession.getProxy(SmsMapper.class);
        //通过代理对象执行sql
        Sms sms = proxy.selectSmsById(1);
        System.out.println(sms);
    }
}
