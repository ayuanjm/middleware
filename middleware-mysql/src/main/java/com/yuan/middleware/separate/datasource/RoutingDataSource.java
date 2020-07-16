package com.yuan.middleware.separate.datasource;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

/**
 * AbstractRoutingDataSource的内部维护了一个名为targetDataSources的Map，
 * 并提供的setter方法用于设置数据源关键字与数据源的关系，实现类被要求实现其determineCurrentLookupKey()方法，
 * 由此方法的返回值决定具体从哪个数据源中获取连接。
 *
 * @author yuanjm
 * @date 2020/7/16 3:06 下午
 */
public class RoutingDataSource extends AbstractRoutingDataSource {

    @Override
    protected Object determineCurrentLookupKey() {
        return DataSourceContextHolder.getDataSource();
    }
}
