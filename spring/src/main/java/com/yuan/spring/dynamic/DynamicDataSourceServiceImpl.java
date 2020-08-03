package com.yuan.spring.dynamic;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author yuanjm
 * @date 2020/7/9 2:44 下午
 */
@Service
public class DynamicDataSourceServiceImpl implements DynamicDataSourceService {
    private static final String docker_sql01 = "INSERT INTO market_activity (name, start_time) VALUES ('001', sysdate())";
    private static final String docker_sql02 = "INSERT INTO market_activity (name, start_time) VALUES ('002', sysdate())";

    @Resource
    private JdbcTemplate db01JdbcTemplate;

    @Resource
    private JdbcTemplate db02JdbcTemplate;


    @Override
    public Object sqlSessionTest() {
        return null;
    }

    @Override
    public Object jdbcTemplateDynamicDataSource() {
        int update01 = db01JdbcTemplate.update(docker_sql01);
        int update02 = db02JdbcTemplate.update(docker_sql02);
        System.out.println(update01);
        System.out.println(update02);
        return null;
    }
}
