package com.yuan.middleware.mybatis.mapper;

import com.yuan.middleware.mybatis.entity.Sms;

/**
 * @author yjm
 * @version 1.0
 * @date 2020/2/28 3:36 下午
 */
public interface SmsMapper {
    /**
     * 通过id查找SMS
     *
     * @param id
     * @return
     */
    Sms selectSmsById(Integer id);
}
