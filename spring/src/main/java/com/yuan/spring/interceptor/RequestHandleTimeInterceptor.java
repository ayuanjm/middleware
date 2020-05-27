package com.yuan.spring.interceptor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
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
@Component
public class RequestHandleTimeInterceptor implements HandlerInterceptor {
    private static final ThreadLocal<Long> CONSUME_TIME = new ThreadLocal<>();

    /**
     * DispatcherServlet.doDispatch()
     *
     * // Determine handler adapter for the current request.
     * HandlerAdapter ha = getHandlerAdapter(mappedHandler.getHandler());
     *
     * // Process last-modified header, if supported by the handler.
     * String method = request.getMethod();
     * boolean isGet = "GET".equals(method);
     * if (isGet || "HEAD".equals(method)) {
     * 	long lastModified = ha.getLastModified(request, mappedHandler.getHandler());
     * 	if (new ServletWebRequest(request, response).checkNotModified(lastModified) && isGet) {
     * 		return;
     *        }
     * }
     *
     * //执行拦截器链的预先处理方法
     * if (!mappedHandler.applyPreHandle(processedRequest, response)) {
     * 	return;
     * }
     *
     * //执行目标方法
     * // Actually invoke the handler.
     * mv = ha.handle(processedRequest, response, mappedHandler.getHandler());
     *
     * if (asyncManager.isConcurrentHandlingStarted()) {
     * 	return;
     * }
     *
     * applyDefaultViewName(processedRequest, mv);
     * //执行拦截器链的后置处理方法
     * mappedHandler.applyPostHandle(processedRequest, response, mv);
     */

    /**
     * boolean applyPreHandle(HttpServletRequest request, HttpServletResponse response) throws Exception {
     * 	HandlerInterceptor[] interceptors = getInterceptors();
     * 	if (!ObjectUtils.isEmpty(interceptors)) {
     * 		for (int i = 0; i < interceptors.length; i++) {
     * 			HandlerInterceptor interceptor = interceptors[i];
     * 			if (!interceptor.preHandle(request, response, this.handler)) {
     * 		        //当前拦截器返回false，会执行前面放行的拦截器的AfterCompletion
     * 				triggerAfterCompletion(request, response, null);
     * 				return false;
     *              }
     * 			this.interceptorIndex = i;
     * 			}
     *    }
     * 	return true;
     * }
     *
     * void triggerAfterCompletion(HttpServletRequest request, HttpServletResponse response, @Nullable Exception ex)
     * 		throws Exception {
     *
     * 	HandlerInterceptor[] interceptors = getInterceptors();
     * 	if (!ObjectUtils.isEmpty(interceptors)) {
     * 	//如果由于拦截器返回false，interceptorIndex的值就是前一个放行拦截器的值
     * 		for (int i = this.interceptorIndex; i >= 0; i--) {
     * 			HandlerInterceptor interceptor = interceptors[i];
     * 			try {
     * 				interceptor.afterCompletion(request, response, this.handler, ex);
     *                        }
     * 			catch (Throwable ex2) {
     * 				logger.error("HandlerInterceptor.afterCompletion threw exception", ex2);
     *            } 		}
     *    }
     * }
     */

    /**
     * 目标方法执行前执行拦截器链的预先处理方法
     * @param request
     * @param response
     * @param handler
     * @return
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        CONSUME_TIME.set(System.currentTimeMillis());
        System.out.println("preHandle");
        return true;
    }

    /**
     * 目标方法执行后执行拦截器链的后置处理方法
     * @param request
     * @param response
     * @param handler
     * @param modelAndView
     */
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) {
        System.out.println("postHandle");
        log.info("METHOD={}, HANDLE_TIME={}ms", request.getRequestURI(), System.currentTimeMillis() - CONSUME_TIME.get());
        //Controller执行完移除该线程的属性threadLocals 存储的key为CONSUME_TIME的值 释放空间
        CONSUME_TIME.remove();
    }

    /**
     * 当前拦截器的postHandle执行后页面渲染render执行后，或者后一个拦截器的preHandle执行返回false会执行前一个拦截器的afterCompletion
     * 也就是已经放行了的拦截器的afterCompletion总会执行
     * @param request
     * @param response
     * @param handler
     * @param ex
     * @throws Exception
     */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        System.out.println("afterCompletion");
    }
}
