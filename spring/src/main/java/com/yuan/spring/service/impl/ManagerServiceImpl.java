package com.yuan.spring.service.impl;

import com.yuan.spring.service.ManagerService;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * @author yuan
 */
@Transactional(rollbackFor = Exception.class)
@Service
public class ManagerServiceImpl implements ManagerService {
    @Resource
    private JdbcTemplate jdbcTemplate;
    @Override
    public String getUser() {
        int update = jdbcTemplate.update("INSERT INTO cloudDB01.dept (dname, db_source, version) VALUES ('12121', '001', 3)");
        System.out.println(update);
        int a = 1/0;
        return "hello world";
    }
}
