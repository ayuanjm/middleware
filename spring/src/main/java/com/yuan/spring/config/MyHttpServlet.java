package com.yuan.spring.config;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * HttpServlet
 * MyHttpServlet 继承了HttpServlet，继承GenericServlet，继承Servlet，一个客户端的请求处理流程完美的运用了多态。
 * 一个客户端请求发送过来永远都是先调用service方法，在httpServlet实现的service方法中调用了自己的service方法。
 * 在自己的service方法中获取请求方法，根据不同的请求方式调用不同的doXXX方法。谁重写了父类的方法就调用最后重写的方法，
 * 如果MyHttpServlet重写了service方法就不会调用HttpServlet重写的service方法了。
 *
 * @author yuan
 * @date 2019/12/09
 */
public class MyHttpServlet extends HttpServlet {
    public MyHttpServlet() {
        System.out.println("构造器");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("doGet");
        //自定义实现Servlet时，正常不区分get post请求方式，在doPost中写处理请求的方法
        doPost(req, resp);
    }

    /**
     * @param req  浏览器封装的请求信息对象
     * @param resp 发送浏览器的响应对象，封装我们的响应信息
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("doPost");
//        //HttpServletResponse作用:
//        //1、可以给浏览器响应字符串
//        resp.getWriter().write("Hello HttpServlet");
//        //2、可以重定向到一个页面或者其他资源，重定向就是服务器告诉浏览器重新请求别的资源,已经对之前的request响应完了。
//        resp.sendRedirect("index.jsp");

//        //HttpServletRequest作用:
//        //http请求 请求首行 请求头 空行 请求体(body 封装的请求数据 - post)
//        //1、获取一对一 localhost:8080/spring/myHttpServlet?name=yuanjm&hobby=girl&hobby=money
//        System.out.println("name:" + req.getParameter("name"));
//        //1、获取一对多 localhost:8080/spring/myHttpServlet?name=yuanjm&hobby=girl&hobby=money
//        //foreach中 循环的hobby不能为null,不然会NullPointerException
//        String[] hobby = req.getParameterValues("hobby");
//        for (String str : hobby) {
//            System.out.println("hobby:" + str);
//        }
        //2、获取请求头信息
        System.out.println("User-Agent:" + req.getHeader("User-Agent"));
        //3、转发一个页面/资源
        //先获取一个请求转发器
        RequestDispatcher requestDispatcher = req.getRequestDispatcher("index.jsp");
        //将请求转发出去
        requestDispatcher.forward(req, resp);
        //4、作为阈对象共享数据request servletContext application
    }
}
