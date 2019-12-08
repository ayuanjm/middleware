package com.yuan.spring.controller;

import com.yuan.spring.service.ManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    @RequestMapping("/demo")
    public String demo() {
        return managerService.getUser();
    }

}
