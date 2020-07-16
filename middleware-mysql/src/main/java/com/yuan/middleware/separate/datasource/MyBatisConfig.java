package com.yuan.middleware.separate.datasource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import javax.annotation.Resource;

/**
 * @author yuanjm
 * @date 2020/7/16 3:21 下午
 */
@Configuration
public class MyBatisConfig {

    @Resource(name = "routingDataSource")
    private RoutingDataSource routingDataSource;

    /**
     * routingDataSource sqlSessionFactory
     *
     * @return
     */
    @Bean
    public SqlSessionFactory sqlSessionFactory() throws Exception {
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(routingDataSource);
        sqlSessionFactoryBean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath:mapper/**/*.xml"));
        sqlSessionFactoryBean.setTypeAliasesPackage("com.yuan.middleware.separate.dao");
        return sqlSessionFactoryBean.getObject();
    }

    /**
     * 注册 sqlSessionTemplate
     *
     * @param sqlSessionFactory
     * @return
     */
    @Bean
    public SqlSessionTemplate financialMasterSqlSessionTemplate(SqlSessionFactory sqlSessionFactory) {
        return new SqlSessionTemplate(sqlSessionFactory);
    }

}
