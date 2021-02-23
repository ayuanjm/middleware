package com.yuan.middleware.spring.interceptor;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

/**
 * 耗时拦截
 *
 * @author yuan
 * @date 2019/12/02
 */
@Slf4j
@Component
public class LogInterceptor implements HandlerInterceptor {
    private final static String TRACE_ID = "traceId";


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        MDC.put(LogInterceptor.TRACE_ID, getTraceId());
        return true;
    }


    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) {
        MDC.remove(LogInterceptor.TRACE_ID);
    }

    private static String getTraceId() {
        return UUID.randomUUID().toString().replace("-", "");
    }
}
