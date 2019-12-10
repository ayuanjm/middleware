package com.yuan.spring.controller;

import com.yuan.spring.service.ManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author yuan
 */
@RestController
public class ServletController {

    @Autowired
    private ManagerService managerService;

    @RequestMapping("/index")
    public String index() {
        return "index";
    }
//
//    @RequestMapping("/myHttpServlet")
//    public String myHttpServlet() {
//        return "hello yuan";
//    }

    @RequestMapping("/demo")
    public ModelAndView demo() {
        return new ModelAndView("success");
    }

}
