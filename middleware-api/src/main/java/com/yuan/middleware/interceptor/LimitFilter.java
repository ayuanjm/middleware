package com.yuan.middleware.interceptor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.*;
import java.io.IOException;

/**
 * @author yuanjm
 * @date 2020/9/10 2:50 下午
 */
@Slf4j
public class LimitFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) {
        log.info("LimitFilter init");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        if (request.getParameter("age") == null) {
            log.info("age is null");
            return;
        }
        chain.doFilter(request, response);
    }
}
