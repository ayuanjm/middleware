package com.yuan.middleware.separate.datasource;

import lombok.extern.slf4j.Slf4j;

/**
 * 数据源切换方法
 * 维护一个static变量datasourceContext用于记录每个线程需要使用的数据源关键字。并提供切换、读取、清除数据源配置信息的方法。
 *
 * @author yuanjm
 * @date 2020/7/16 3:05 下午
 */
@Slf4j
public class DataSourceContextHolder {
    /**
     * 维护一个static变量datasourceContext用于记录每个线程需要使用的数据源关键字。并提供切换、读取、清除数据源配置信息的方法。
     */
    private static ThreadLocal<String> datasourceContext = new ThreadLocal<>();

    /**
     * 维护一个变量记录线程是否使用过事务
     */
    private static ThreadLocal<String> transactionalMaster = new ThreadLocal<>();

    public static void switchDataSource(String datasource) {
        log.debug("switchDataSource: {}", datasource);
        datasourceContext.set(datasource);
    }

    public static String getDataSource() {
        return datasourceContext.get();
    }

    public static void clear() {
        datasourceContext.remove();
    }

    /**
     * 在一个事务中第一次使用master
     */
    public static void setMasterCount() {
        transactionalMaster.set("master");
    }

    public static String getMasterCount() {
        return transactionalMaster.get();
    }

    /**
     * 用完后移除ThreadLocal值，防止内存泄露
     */
    public static void clearMasterCount() {
        transactionalMaster.remove();
    }
}
