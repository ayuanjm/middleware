package com.yuan.spring.service.impl;

import com.yuan.spring.service.ManagerServiceA;
import com.yuan.spring.service.ManagerServiceB;
import org.springframework.aop.framework.AopContext;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * @author yuan
 */
@Transactional(rollbackFor = Exception.class)
@Service
public class ManagerServiceImplB implements ManagerServiceB {
    private static final String docker_sql = "INSERT INTO cloudDB01.dept (dname, db_source, version) VALUES ('12121', '001', 3)";
    private static final String my_sql = "INSERT INTO sales_platform.td_ms_sms (create_time, phone, user_name) VALUES ( sysdate(), '121', '11')";
    @Resource
    private JdbcTemplate jdbcTemplate;

    @Resource
    private ManagerServiceA managerServiceA;

    @Override
    public Integer getUser() {
        int update = jdbcTemplate.update(my_sql);
        managerServiceA.getUser();
        return update;
    }

    @Override
    public Integer insertUser() {
        int update = jdbcTemplate.update(my_sql);
        int a = 1 / 0;
        return update;
    }
}
