package com.yuan.spring.service.impl;

import com.yuan.spring.service.ManagerService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author yuan
 */
@Transactional(rollbackFor = Exception.class)
@Service
public class ManagerServiceImpl implements ManagerService {
    @Override
    public String getUser() {
        return "hello world";
    }
}
