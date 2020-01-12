package com.yuan.spring.config;

import javax.servlet.http.*;
import java.io.IOException;

/**
 * 服务器端保存数据的技术，域对象(在整个会话期间保存的数据在任意资源都可以取到)
 * 服务器在运行时可以为每一个用户的浏览器创建一个其独享的session对象，由于session为用户浏览器独享，所以用户在访问服务器的web资源时，
 * 可以把各自的数据放在各自的session中，当用户再去访问服务器中的其它web资源时，其它web资源再从用户各自的session中取出数据为用户服务。
 * 在第一次调用request.getSession()方法时，服务器会检查是否已经有对应的session，如果没有就在内存中创建一个并返回。
 * 当一段时间内，session没有被使用（默认是30分钟），服务器会销毁该session。如果服务器非正常关闭（强行关闭），还未到期的session也会被销毁。
 * 另外，调用session的invalidate()方法可以立即销毁session。
 * session的作用范围:一次会话
 *
 * @author yuan
 * @date 2020/01/12
 */
public class SessionServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        HttpSession session = req.getSession();
        System.out.println("获取到session:" + session);
        /**
         * 浏览器一开一关就是新的会话，每一次新的会话都会产生新的session。
         * 在第一次调用request.getSession()方法时，服务器会检查是否已经有对应的session，如果没有就在内存中创建一个并返回。（只有调用这个方法才会创建session)
         * 服务器第一次给浏览器会话创建session时，会命令浏览器保存session的id JESSIONID，浏览器以后每次访问服务器都会带上JESSIONID，这样就可以获取对应的session
         */
        boolean isNew = session.isNew();
        System.out.println("session是否是新的:" + isNew);
        resp.getWriter().write(session.toString());
        /**
         * 获取session的最大存活时间，以秒为单位
         * session默认是30分钟，为什么新会话开启会返回新的session
         * 因为session根据cookie带来的JESSIONID来获取，cookie默认关闭浏览器就没了
         * 新的会话再来获取session，由于原来的JESSIONID没了找不到原来的session,就返回新的session，旧的session还在，只是找不到
         */
        int maxInactiveInterval = session.getMaxInactiveInterval();
        System.out.println(maxInactiveInterval);
        /**
         * 设置session过期时间，以秒为单位
         * 1、传入负数，代表永不过期
         * 2、传入正数，代表多少秒以后过期，距离最后一次使用session时间开始计时
         */
        session.setMaxInactiveInterval(60);

        /**
         * 浏览器关闭也可以保持session，下次访问还能访问到之前的session
         * 将JESSIONID这个cookie持久化
         */
        String id = session.getId();
        //覆盖JESSIONID的值
        Cookie cookie = new Cookie("JESSIONID", id);
        //设置cookie的持久时间,以秒为单位
        cookie.setMaxAge(60 * 60);
        //返回cookie给浏览器
        resp.addCookie(cookie);
        /**
         * cookie 被禁用导致会话获取session失效，每次都生成新的session
         * 可以使用url重写 把要访问的地址动态的加上JESSIONID
         * response.encodeRedirectRul()
         */

        /**
         * session的活化和钝化
         * 现象：服务器正常关闭重新启动，只要浏览器没关，还是能获取到session里面的内容
         * 钝化：服务器关闭以后，会将session（序列化）保存硬盘中，可以在当前项目tomcat路径下观察session.ser文件
         * 活化：服务器再次启动时，把之前的序列化好的文件加载进来，就会再次加载之前保存的session
         *      session.ser包含了session域中的所有内容
         * session域中的对象要能同session一起钝化到磁盘中必须实现序列化接口 Serializable
         *
         */

    }
}
