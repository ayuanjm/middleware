package com.yuan.middleware.interceptor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 耗时拦截
 *
 * @author yuan
 * @date 2019/12/02
 */
@Slf4j
public class RequestHandleTimeInterceptor implements HandlerInterceptor {
    private static final ThreadLocal<Long> CONSUME_TIME = new ThreadLocal<>();

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        CONSUME_TIME.set(System.currentTimeMillis());
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) {
        log.info("METHOD={}, HANDLE_TIME={}ms", request.getRequestURI(), System.currentTimeMillis() - CONSUME_TIME.get());
        //Controller执行完移除该线程的属性threadLocals 存储的key为CONSUME_TIME的值 释放空间
        CONSUME_TIME.remove();
    }

}
