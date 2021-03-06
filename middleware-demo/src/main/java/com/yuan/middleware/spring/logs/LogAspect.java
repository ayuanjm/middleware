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
 * Order 切面执行顺序，值越大优先级越低
 *
 * @author yuanjm
 * @date 2020/03/17 11:06
 */
@Aspect
@Component
@Order(1)
public class LogAspect {

    private Logger logger = LoggerFactory.getLogger(LogAspect.class);

    private static final ThreadLocal<Long> startTime = new ThreadLocal<>();

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
        joinPoint.proceed();
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
        /**
         *  https://www.nowcoder.com/discuss/69456
         *  移除本次请求的key，value，防止出现由ThreadLocal导致的内存泄露,但是这里的threadLocal是私有静态的，引用会随着方法区的类信息一起生存，
         *  而且这个类经常被使用，极低的概率会出现类的卸载，因此也不大可能出现强引用失效，导致内部弱引用回收key，key为null,获取不到对应value，
         *  导致value无法被回收，产生内存泄露。
         *  查看源码会发现，ThreadLocal的get、set和remove方法都实现了对所有key为null的value的清除，但仍可能会发生内存泄露，
         *  因为可能使用了ThreadLocal的get或set方法后发生GC，此后不调用get、set或remove方法，为null的value就不会被清除。
         */
        startTime.remove();
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
