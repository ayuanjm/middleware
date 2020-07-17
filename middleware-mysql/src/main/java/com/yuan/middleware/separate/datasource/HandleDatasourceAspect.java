package com.yuan.middleware.separate.datasource;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * 使用aop根据注解name无侵入切换数据源
 * 注意：当方法没有加注解时，取的是默认数据源，也就是masterDataSource
 * <p>
 * Order(1)让自定义切面优先级高于spring事务执行，否则spring事务先执行，会创建事务和绑定数据库连接， 这个时候的DataSource由于还没有key值，就会取默认的master，这样后面执行带有salve注解的方法也不会取salve库
 * 会从threadLocal中取到和事务绑定的数据库连接。
 * 如果定义了@Order(1)，就可以在创建事务时，获取的数据库连接根据注解动态取得。
 *
 * @author yuanjm
 * @date 2020/7/16 3:39 下午
 */
@Aspect
@Order(1)
@Component
public class HandleDatasourceAspect {

    @Pointcut("@annotation(com.yuan.middleware.separate.datasource.DataSource)")
    public void pointcut() {
    }

    @Before("pointcut()")
    public void beforeExecute(JoinPoint joinPoint) {
        Method method = ((MethodSignature) joinPoint.getSignature()).getMethod();
        //获取方法的注解信息
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
