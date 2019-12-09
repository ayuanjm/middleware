package com.yuan.spring.config;

import javax.servlet.*;
import java.io.IOException;

/**
 * 自定义Servlet处理指定请求
 * <p>
 * 第一次访问MyServlet时:
 * 1、创建一个MyServlet对象（调用构造方法）
 * 2、调用init方法
 * 3、调用service方法
 * <p>
 * 以后访问时:
 * 4、只调用service方法 整个运行期间只创建了一个servlet对象。
 * servlet是单实例，多线程运行，不能写共享变量。
 * <p>
 * 当项目从服务器上卸载:
 * 5、服务器调用destroy方法
 *
 * @author yuan
 * @date 2019/12/08
 */
public class MyServlet implements Servlet {
    public MyServlet() {
        System.out.println("servlet 构造器");
    }

    /**
     * 初始化
     *
     * @param servletConfig 封装了servlet配置信息的对象，一个Servlet对应一个ServletConfig
     * @throws ServletException
     */
    @Override
    public void init(ServletConfig servletConfig) {
        System.out.println("servlet init 初始化");
        //获取servlet别名
        System.out.println(servletConfig.getServletName());
        //获取servlet初始化参数
        System.out.println(servletConfig.getInitParameter("userName"));
        /**
         * 获取ServletContext对象，代表当前servlet的上下文。代表我当前的web项目信息
         * 一个web应用对应一个ServletContext，代表整个web项目。
         * 1、可以获取web项目的配置信息，获取web项目的初始化参数。
         * 2、获取web的项目路径
         * 3、获取资源的真实路径
         *     虚拟路径：是网络访问使用的虚拟路径，每一个虚拟路径应该对应一个实际的资源。
         *     静态资源：(文件的形式)，动态资源(只是启用一段程序代码)
         *     真实路径：文件在磁盘中的存储路径
         * 4、可以作为最大的域对象共享数据 域对象：共享数据4大对象。application域对象
         */
        ServletContext servletContext = servletConfig.getServletContext();
        String contextConfigLocation = servletContext.getInitParameter("contextConfigLocation");
        //取不到，因为userName是MyServlet的参数
        String userName = servletContext.getInitParameter("userName");
        System.out.println("contextConfigLocation:" + contextConfigLocation);
        System.out.println("userName:" + userName);
        System.out.println("获取web的项目路径:" + servletContext.getContextPath());
        String realPath = servletContext.getRealPath("/index.jsp");
        System.out.println("真实路径：" + realPath);
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
        System.out.println("servlet 销毁");
    }
}
