package com.yuan.middleware.jdk.mybatis.executor;

public interface Executor {
    <T> T query(String statement, Object parameter);
}
