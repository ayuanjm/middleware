package com.yuan.spring.service.impl;

import com.yuan.spring.dao.SmsMapper;
import com.yuan.spring.service.ManagerServiceA;
import com.yuan.spring.service.SmsService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author yuanjm
 * @date 2020/7/9 2:44 下午
 */
@Service
public class SmsServiceImpl implements SmsService {

    @Resource
    private SmsMapper smsMapper;

    @Resource
    private ManagerServiceA managerServiceA;

    @Override
    public Object sqlSessionTest() {
        System.out.println(1111111111);
        smsMapper.getSmsById();
        System.out.println(1111111111);
        smsMapper.getSmsId();
        System.out.println(1111111111);
        smsMapper.getSmsById();
        System.out.println(1111111111);
        return null;
    }
}
