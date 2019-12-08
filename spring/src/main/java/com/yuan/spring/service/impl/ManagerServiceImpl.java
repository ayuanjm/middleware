package com.yuan.spring.service.impl;

import com.yuan.spring.service.ManagerService;
import org.springframework.stereotype.Service;

/**
 * @author yuan
 */
@Service
public class ManagerServiceImpl implements ManagerService {
    @Override
    public String getUser() {
        return "hello world";
    }
}
