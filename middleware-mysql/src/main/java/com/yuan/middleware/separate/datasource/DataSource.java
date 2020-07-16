package com.yuan.middleware.separate.datasource;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 动态切换数据源注解
 *
 * @author yuanjm
 * @date 2020/7/16 11:17 上午
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface DataSource {
    String name() default "";
}
