package com.yuan.spring.service.impl;

import com.yuan.spring.service.ManagerServiceA;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * @author yuan
 */
@Transactional(propagation = Propagation.SUPPORTS,rollbackFor = Exception.class)
@Service
public class ManagerServiceImplA implements ManagerServiceA {
    private static final String docker_sql = "INSERT INTO cloudDB01.dept (dname, db_source, version) VALUES ('12121', '001', 3)";
    private static final String my_sql = "INSERT INTO sales_platform.td_ms_sms (create_time, phone, user_name)\n" +
            "VALUES ( sysdate(), '121', '11')";
    @Resource
    private JdbcTemplate jdbcTemplate;
    @Override
    public Integer getUser() {
        int update = jdbcTemplate.update(my_sql);
        int a = 1/0;
        return update;
    }

    @Override
    public Integer insertUser() {
        int update = jdbcTemplate.update(my_sql);
        return update;
    }
}
