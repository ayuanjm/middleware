package com.yuan.middleware.separate.datasource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import javax.annotation.Resource;

/**
 * Mybatis配置
 * <p>
 * ConnectionHolder conHolder = (ConnectionHolder) TransactionSynchronizationManager.getResource(dataSource);
 * 执行sql时会通过这个语句去获取数据库连接，如果有事务，那么在创建了事务后会从数据库连接池拿一个数据库连接，
 * 把数据库连接和当前事务线程绑定放入threadLocal中，之后执行sql语句时就会从threadLocal中获取,就不会再拿新的数据库连接。
 * key是resources，value是map，map:{routingDataSource:connectionHolder,DefaultSessionFactory,SqlSessionHolder}
 * ThreadLocal<Map<Object, Object>> resources = new NamedThreadLocal<>("Transactional resources");
 * 如果没有事务或者有事务但是routingDataSource被改变，这样去threadLocal中就拿不到数据库连接，机会去创建新的数据库连接。
 *
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
