package com.mengxuegu.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MainController {

//首页
    @RequestMapping({"/index","/",""})
    public String index() {
        return "index";
    }
}
