package com.yuan.middleware.separate.datasource;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

/**
 * @author yuanjm
 * @date 2020/7/16 3:10 下午
 */
@Configuration
public class DataSourceConfig {
    /**
     * @Primary: DataSource接口有两个实现类，masterDataSource、slaveDataSource
     * 当使用
     * @Autowired/@Resource DataSource dataSource注入属性时会优先取masterDataSource
     * 而不会报错expected single matching bean but found 2
     * <p>
     * 在自动配置类DataSourceTransactionManagerAutoConfiguration中，通过@ConditionalOnSingleCandidate(DataSource.class)
     * 会取得@Primary注解的dataSource属性，也就是我们配置的masterDataSource赋值给HikariDataSource，然后创建事务管理器bean，赋值dataSource属性
     * DataSourceTransactionManager transactionManager = new DataSourceTransactionManager(this.dataSource);
     */
    @Bean
    @ConfigurationProperties(prefix = "datasource.druid.master")
    public DataSource masterDataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean
    @ConfigurationProperties(prefix = "datasource.druid.slave")
    public DataSource slaveDataSource() {
        return DataSourceBuilder.create().build();
    }

    /**
     * Primary必须放在RoutingDataSource上，这样才能从3个DataSource的实现类里取到routingDataSource
     * 才能设置成DataSourceTransactionManage的DataSource属性，才可以在有事务时也能切换数据源。
     */
    @Primary
    @Bean(name = "routingDataSource")
    public RoutingDataSource routingDataSource() {
        //将数据源放入AbstractRoutingDataSource的内部targetDataSources的Map中
        Map<Object, Object> dataSourceMap = new HashMap<>(16);
        dataSourceMap.put("master", masterDataSource());
        dataSourceMap.put("slave", slaveDataSource());

        RoutingDataSource routingDataSource = new RoutingDataSource();
        //最终会在InitializingBean方法中赋值给Map<Object, DataSource> resolvedDataSources
        routingDataSource.setTargetDataSources(dataSourceMap);
        //设置默认数据源，当resolvedDataSources为null时使用masterDataSource作为数据源
        routingDataSource.setDefaultTargetDataSource(masterDataSource());
        return routingDataSource;
    }
}
