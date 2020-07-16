package com.yuan.middleware.jdk.mybatis.executor.impl;

import com.yuan.middleware.jdk.mybatis.entity.Sms;
import com.yuan.middleware.jdk.mybatis.executor.Executor;

import java.sql.*;

/**
 * 封装JDBC实现CRUD
 *
 * @author yuan
 */
public class ExecutorImpl implements Executor {
    @Override
    public <T> T query(String sql, Object parameter) {
        Connection connection;
        PreparedStatement preparedStatement;
        connection = getConnection();

        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, (Integer) parameter);
            ResultSet resultSet = preparedStatement.executeQuery();
            Sms sms = new Sms();
            while (resultSet.next()) {
                sms.setId(resultSet.getInt(1));
                sms.setPhone(resultSet.getString(2));
            }
            return (T) sms;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    private Connection getConnection() {
        String driver = "com.mysql.cj.jdbc.Driver";
        String url = "jdbc:mysql://localhost:3316/yuan";
        String username = "root";
        String password = "123456Aa";

        try {
            Class.forName(driver);
            Connection connection = DriverManager.getConnection(url, username, password);
            return connection;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
