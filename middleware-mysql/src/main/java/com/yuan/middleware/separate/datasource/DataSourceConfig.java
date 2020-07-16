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

    @Bean
    @Primary
    @ConfigurationProperties(prefix = "datasource.druid.master")
    public DataSource masterDataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean
    @ConfigurationProperties(prefix = "datasource.druid.slave")
    public DataSource slaveDataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean(name = "routingDataSource")
    public RoutingDataSource routingDataSource() {
        Map<Object, Object> dataSourceMap = new HashMap<>(16);
        dataSourceMap.put("master", masterDataSource());
        dataSourceMap.put("slave", slaveDataSource());

        RoutingDataSource routingDataSource = new RoutingDataSource();
        routingDataSource.setTargetDataSources(dataSourceMap);
        routingDataSource.setDefaultTargetDataSource(masterDataSource());
        return routingDataSource;
    }
}
