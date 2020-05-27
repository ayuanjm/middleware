//package com.yuan.spring.aop;
//
//import org.aspectj.lang.JoinPoint;
//import org.aspectj.lang.ProceedingJoinPoint;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.stereotype.Component;
//import org.springframework.web.context.request.RequestContextHolder;
//import org.springframework.web.context.request.ServletRequestAttributes;
//
//import javax.servlet.http.HttpServletRequest;
//import java.util.Arrays;
//
///**
// * 由于本项目有些jar包依赖springboot导致在使用外置tomcat启动项目时会报错
// * 01:00:38.548 [RMI TCP Connection(2)-127.0.0.1] DEBUG org.springframework.context.annotation.ConfigurationClassUtils - Failed to introspect @Bean methods on class [org.springframework.aop.aspectj.AspectJExpressionPointcut]: java.lang.IllegalStateException: Failed to introspect annotated methods on class org.springframework.aop.aspectj.AspectJExpressionPointcut
// * 使用main方法启动时不报错 ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
// * 因此使用tomcat启动时先注释切面
// * 统一日志处理
// * Order 切面执行顺序，值越大优先级越低
// *
// * @author yuanjm
// * @date 2020/03/17 11:06
// */
//@Component
//public class LogAspect {
//
//    private Logger logger = LoggerFactory.getLogger(LogAspect.class);
//
//    private static final ThreadLocal<Long> startTime = new ThreadLocal<>();
//
//
//    public void around(ProceedingJoinPoint joinPoint) throws Throwable {
//        System.out.println("around start");
//        //执行目标方法
//        joinPoint.proceed();
//        System.out.println("around end");
//
//    }
//
//    /**
//     * 在切点前执行
//     *
//     * @param joinPoint
//     */
//    public void doBefore(JoinPoint joinPoint) {
//        startTime.set(System.currentTimeMillis());
//        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
//        HttpServletRequest request = requestAttributes.getRequest();
//        String url = request.getRequestURL().toString();
//        String httpMethod = request.getMethod();
//        String ip = getIpAddr(request);
//        String classMethod = joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName();
//        String parameters = Arrays.toString(joinPoint.getArgs());
//        logger.info("REQUEST URL:" + url + " | HTTP METHOD: " + httpMethod + " | IP: " + ip + " | CLASS_METHOD: " + classMethod
//                + " | ARGS:" + parameters);
//    }
//
//    /**
//     * 在切点后，return前执行
//     *
//     * @param joinPoint
//     */
//    public void doAfter(JoinPoint joinPoint) {
//    }
//
//    /**
//     * 在切入点，return后执行，如果相对某些方法的返回参数进行处理，可以在此处执行
//     *
//     * @param object
//     */
//    public void doAfterReturning(Object object) {
//        logger.info("RESPONSE TIME: " + (System.currentTimeMillis() - startTime.get()) + "ms");
//        logger.info("RESPONSE BODY: " + object);
//        /**
//         *  https://www.nowcoder.com/discuss/69456
//         *  移除本次请求的key，value，防止出现由ThreadLocal导致的内存泄露,但是这里的threadLocal是私有静态的，引用会随着方法区的类信息一起生存，
//         *  而且这个类经常被使用，极低的概率会出现类的卸载，因此也不大可能出现强引用失效，导致内部弱引用回收key，key为null,获取不到对应value，
//         *  导致value无法被回收，产生内存泄露。
//         *  查看源码会发现，ThreadLocal的get、set和remove方法都实现了对所有key为null的value的清除，但仍可能会发生内存泄露，
//         *  因为可能使用了ThreadLocal的get或set方法后发生GC，此后不调用get、set或remove方法，为null的value就不会被清除。
//         */
//        startTime.remove();
//    }
//
//    /**
//     * 获取真实的IP地址
//     *
//     * @param request
//     * @return
//     */
//    private String getIpAddr(HttpServletRequest request) {
//        String ip = request.getHeader("x-forwarded-for");
//        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
//            ip = request.getHeader("Proxy-Client-IP");
//        }
//        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
//            ip = request.getHeader("WL-Proxy-Client-IP");
//        }
//        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
//            ip = request.getRemoteAddr();
//        }
//        return ip;
//    }
//}
