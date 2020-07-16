package com.yuan.middleware.jdk.base.cookie;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * @author yuan
 * @date 2019/12/08
 */
@RestController
public class CookieDemo {

    @GetMapping(value = "/yuan")
    public String yuan(HttpServletRequest request) {
        HttpSession session = request.getSession();
        session.setAttribute("yuan", "yuan");
        return "index";
    }

    @GetMapping(value = "/test")
    public String test(HttpServletRequest request) {
        HttpSession session = request.getSession();
        session.setAttribute("test", "test");
        return "redirect:" + "http://localhost:8180/manage/yuan";
    }

}
