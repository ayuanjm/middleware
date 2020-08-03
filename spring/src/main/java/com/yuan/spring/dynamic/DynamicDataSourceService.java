package com.yuan.spring.dynamic;

/**
 * @author yuanjm
 * @date 2020/7/9 2:44 下午
 */
public interface DynamicDataSourceService {
    Object sqlSessionTest();

    Object jdbcTemplateDynamicDataSource();
}
