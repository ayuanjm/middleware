package com.yuan.spring.config;

import javax.servlet.*;
import java.io.IOException;

/**
 * 自定义Servlet处理指定请求
 *
 * @author yuan
 * @date 2019/12/08
 */
public class MyServlet implements Servlet {
    /**
     * 初始化
     *
     * @param servletConfig
     * @throws ServletException
     */
    @Override
    public void init(ServletConfig servletConfig) {
        System.out.println("servlet init 初始化");
    }

    /**
     * 获取servlet配置信息
     *
     * @return
     */
    @Override
    public ServletConfig getServletConfig() {
        return null;
    }

    /**
     * 服务：接收客户端请求
     *
     * @param servletRequest
     * @param servletResponse
     * @throws ServletException
     * @throws IOException
     */
    @Override
    public void service(ServletRequest servletRequest, ServletResponse servletResponse) throws ServletException, IOException {
        System.out.println("service 接收客户端请求 处理请求");
        //解决乱码
        servletResponse.setCharacterEncoding("UTF-8");
        servletResponse.setContentType("text/html;charset=utf-8");
        servletResponse.getWriter().write("自定义的servlet");
    }

    /**
     * 获取servlet信息
     *
     * @return
     */
    @Override
    public String getServletInfo() {
        return null;
    }

    /**
     * 销毁
     */
    @Override
    public void destroy() {

    }
}
