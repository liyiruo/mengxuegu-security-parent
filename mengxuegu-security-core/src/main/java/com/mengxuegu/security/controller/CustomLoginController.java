package com.mengxuegu.security.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 登录处理
 */
@Controller
public class CustomLoginController {
    /**
     * 前往认证页面
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("login/page")
    public String toLogin(HttpServletRequest request, HttpServletResponse response) {
    // 响应一个登录页面 classpath: /templates/login.html
        return "login";
    }
}
