package com.yuan.middleware.spring.logs;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

/**
 * 统一日志处理
 *
 * @author yuanjm
 * @date 2020/03/17 11:06
 */
@Aspect
@Component
@Order(1)
public class LogAspect {

    private Logger logger = LoggerFactory.getLogger(LogAspect.class);

    ThreadLocal<Long> startTime = new ThreadLocal<Long>();

    @Pointcut("execution(public * com.yuan.middleware.spring.controller.*.*(..))")
    public void logPointCut() {
    }

    /**
     * 环绕通知测试
     */
    @Pointcut("execution(public * com.yuan.middleware.spring.service.*.*(..))")
    public void aroundPointCut() {
    }

    @Around("aroundPointCut()")
    public void around(ProceedingJoinPoint joinPoint) throws Throwable {
        System.out.println("around start");
        //执行目标方法
//        joinPoint.proceed();
        System.out.println("around end");

    }

    /**
     * 在切点前执行
     *
     * @param joinPoint
     */
    @Before("logPointCut()")
    public void doBefore(JoinPoint joinPoint) {
        startTime.set(System.currentTimeMillis());
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = requestAttributes.getRequest();
        String url = request.getRequestURL().toString();
        String httpMethod = request.getMethod();
        String ip = getIpAddr(request);
        String classMethod = joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName();
        String parameters = Arrays.toString(joinPoint.getArgs());
        logger.info("REQUEST URL:" + url + " | HTTP METHOD: " + httpMethod + " | IP: " + ip + " | CLASS_METHOD: " + classMethod
                + " | ARGS:" + parameters);
    }

    /**
     * 在切点后，return前执行
     *
     * @param joinPoint
     */
    @After("logPointCut()")
    public void doAfter(JoinPoint joinPoint) {
    }

    /**
     * 在切入点，return后执行，如果相对某些方法的返回参数进行处理，可以在此处执行
     *
     * @param object
     */
    @AfterReturning(returning = "object", pointcut = "logPointCut()")
    public void doAfterReturning(Object object) {
        logger.info("RESPONSE TIME: " + (System.currentTimeMillis() - startTime.get()) + "ms");
        logger.info("RESPONSE BODY: " + object);
    }

    /**
     * 获取真实的IP地址
     *
     * @param request
     * @return
     */
    private String getIpAddr(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }
}
