package com.yuan.middleware.separate.datasource;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * @author yuanjm
 * @date 2020/7/16 3:39 下午
 */
@Aspect
@Component
public class HandleDatasourceAspect {

    @Pointcut("@annotation(com.yuan.middleware.separate.datasource.DataSource)")
    public void pointcut() {
    }

    @Before("pointcut()")
    public void beforeExecute(JoinPoint joinPoint) {
        Method method = ((MethodSignature) joinPoint.getSignature()).getMethod();
        DataSource annotation = method.getAnnotation(DataSource.class);
        if (null == annotation) {
            annotation = joinPoint.getTarget().getClass().getAnnotation(DataSource.class);
        }
        if (null != annotation) {
            // 切换数据源
            DataSourceContextHolder.switchDataSource(annotation.name());
        }
    }

    @After("pointcut()")
    public void afterExecute() {
        DataSourceContextHolder.clear();
    }
}
