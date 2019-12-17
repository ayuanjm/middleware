package com.yuan.spring.config;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLDecoder;
import java.net.URLEncoder;

/**
 * Cookie在浏览器端缓存数据的一种技术
 *
 * @author yuan
 * @date 2019/12/17
 */
public class CookieServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//      createCookie(resp);
//        deleteCookie(req, resp);
        setPath(req, resp);
    }

    /**
     * 设置cookie的保存路径
     *
     * @param req
     * @param resp
     * @throws IOException
     */
    private void setPath(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        //创建cookie
        Cookie cookie = new Cookie("Christmas", "12/24");
        //表示访问yuan下的资源都会带上此cookie，/代表服务器的根目录 http://localhost:8080
        // /yuan告诉浏览器访问哪些路径带上此cookie
        cookie.setPath("/yuan");
        //默认访问当前项目下所有资源都会携带
        resp.setCharacterEncoding("utf-8");
        resp.setContentType("text/html;charset=utf-8");
        resp.addCookie(cookie);
        resp.getWriter().write("cookie 保存路径被修改了");
    }

    /**
     * 删除cookie
     *
     * @param req
     * @param resp
     * @throws IOException
     */
    private void deleteCookie(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Cookie[] cookies = req.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                String name = cookie.getName();
                String value = cookie.getValue();
                if ("userName".equals(name)) {
                    //负数不保存cookie 即使发给浏览器也不会保存(但是大部分浏览器不起作用还是会保存，设置了负数还是在会话期间起作用)
                    //正数 cookie的最大存在时间 单位为second
                    cookie.setMaxAge(0);
                    //修改后需要通知浏览器
                    resp.addCookie(cookie);
                }
                resp.setCharacterEncoding("utf-8");
                resp.getWriter().write("name:" + name);
                resp.setContentType("text/html;charset=utf-8");
                resp.getWriter().write("value:" + URLDecoder.decode(value, "utf-8"));
            }
        }
    }

    /**
     * 创建cookie
     *
     * @param resp
     * @throws IOException
     */
    private void createCookie(HttpServletResponse resp) throws IOException {
        //服务器创建一个cookie
        String s = URLEncoder.encode("往后的日子平淡安稳", "utf-8");
        Cookie cookie = new Cookie("userName", s);
        //持久话cookie 一小时有效，关闭浏览器还是有效
        //没有setMaxAge 一个会话内有效，默认关闭浏览器失效
        cookie.setMaxAge(60 * 60);
        //把cookie发送给浏览器
        resp.addCookie(cookie);
        resp.getWriter().write("hello world");
    }
}
