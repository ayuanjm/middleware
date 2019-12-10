package com.yuan.spring.config;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 请求和重定向
 * 一个请求只能做一次响应，转发和重定向只能有一个存在
 * 区别：
 * 请求浏览器地址栏：转发 不会变化，重定向 会变化
 * request：转发 同一个请求，重定向 两次请求
 * api：转发 request对象，重定向 response对象
 * 位置：转发 服务器内部完成，重定向 浏览器完成
 * WEB-INF：转发 可以访问，重定向 不能访问
 * 共享请求域数据：转发 可以共享，重定向 不可以共享
 * 目标资源：转发 必须是当前web应用中的资源，重定向 不局限于当前web应用
 *
 * @author yuan
 * @date 2019/12/10
 */
public class ChangeHttpServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("User浏览器信息:" + req.getHeader("User-Agent"));
        // 返回index.jsp页面，只能响应一个
        // 1、重定向就是告诉浏览器重新请求一个资源
        resp.sendRedirect("http://www.baidu.com");
        /**
         * 2、转发到页面
         * 转发：服务器处理完成后转发到另一个资源。当我们转发的资源是一个页面资源
         * (静态资源)，服务器会给浏览器返回这个资源
         * 当转交给下一个servlet(动态资源)，servlet可以继续处理
         * WEB-INF下的资源受保护重定向访问不了，转发可以访问
         */
        req.getRequestDispatcher("WEB-INF/views/success.jsp").forward(req, resp);
    }
}
