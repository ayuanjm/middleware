package com.yuan.middleware.mybatis.entity;

import java.util.concurrent.ConcurrentHashMap;

/**
 * 模拟xml解析器读取SmsMapper.xml
 *
 * @author yjm
 * @version 1.0
 * @date 2020/2/28 3:32 下午
 */
public class SmsMapperXml {
    /**
     * 对应SmsMapper.xml中的nameSpace
     */
    public static final String nameSpace = "com.yuan.middleware.mybatis.mapper.SmsMapper";

    public static final ConcurrentHashMap<String,String> map = new ConcurrentHashMap();

    static {
        // 模拟SmsMapper.xml中的id和sql，mybatis是通过xml解析器读取的，这里就直接写死。
        map.put("selectSmsById", "SELECT t.id, t.phone FROM td_ms_sms t where t.id = ?");
    }
}
