package com.yuan.middleware.mybatis.executor;

public interface Executor {
    <T> T query(String statement, Object parameter);
}
