package com.yuan.spring.aop;

import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.transaction.support.TransactionTemplate;

import java.sql.Connection;
import java.sql.PreparedStatement;

/**
 * 编程式事务管理
 *
 * @author yjm
 * @date 2020/4/26 11:34 上午
 */
public class SpringTransactionExample {
    private static final String url = "jdbc:mysql://172.31.20.2:3306/sales_platform?useUnicode=true&characterEncoding=utf-8&useSSL=false";
    private static final String userName = "zeroser";
    private static final String passWorld = "zeroser321";

    public static void main(String[] args) {
        DriverManagerDataSource dataSource = new DriverManagerDataSource(url, userName, passWorld);
        TransactionTemplate template = new TransactionTemplate();
        template.setTransactionManager(new DataSourceTransactionManager(dataSource));
        template.execute(status -> {
            Object savePoint = null;
            try {
                /**
                 * 通过DataSourceUtils.getConnection(dataSource)获取的数据库连接和status里面的数据库连接是同一个，
                 * 查看源码可知，获取的是ThreadLocal<Map<Object, Object>> resources = new NamedThreadLocal<>("Transactional resources");
                 * 取的是本地线程中的数据库连接，是和status同一个连接，获取本地线程中的Map<Object, Object>，map的key为DriverManagerDataSource对象，
                 * 因为status和DataSourceUtils.getConnection(dataSource)是同一个DriverManagerDataSource对象，所以获取的value数据库连接也是同一个，
                 * TransactionStatus status = this.transactionManager.getTransaction(this);status是事务状态
                 * result = action.doInTransaction(status);就是开始执行status -> {}里面自己写的代码是一个lambda表达式
                 * 由于处于同一个事务中，去threadLocal中拿数据库链接也是同一个。
                 */
                Connection connection = DataSourceUtils.getConnection(dataSource);
                /**
                 * 通过dataSource.getConnection()获取的数据库连接和status里面的数据库连接是不是同一个，
                 * 查看源码可知，dataSource.getConnection()是获取一个全新的连接，底层调用getConnectionFromDriver
                 * connection = dataSource.getConnection();
                 */
                { //插入记录
                    PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO sales_platform.td_ms_sms (create_time, phone, user_name)\n" +
                            "VALUES (null, '222', '22')");
                    preparedStatement.executeUpdate();
                }
                //设置保存点,可以灵活的回滚事务，比如当方法发生异常，如果保存点不为null说明保存点之前的sql执行没有问题，回滚事务时可以不回滚保存点之前的sql
                savePoint = status.createSavepoint();
                { //插入记录
                    PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO sales_platform.td_ms_sms (create_time, phone, user_name)\n" +
                            "VALUES (null, '111', '11')");
                    preparedStatement.executeUpdate();
                }
                int a = 1 / 0;
            } catch (Exception e) {
                System.out.println("插入失败");
                if (savePoint != null) {
                    //将当前事务回滚到保存点之前，也就是保存点之前的sql还是有效的，没有被回滚。
                    status.rollbackToSavepoint(savePoint);
                } else {
                    //回滚当前事务内对数据库的所有操作
                    status.setRollbackOnly();
                }
            }
            return null;
        });
    }
}
