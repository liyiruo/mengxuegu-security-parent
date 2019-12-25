package com.mengxuegu.web.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

@Controller
public class MainController {

//首页
    @RequestMapping({"/index","/",""})
    public String index(Map<String,Object>map) {
        // 方式1: 获取登录用户信息
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(principal != null && principal instanceof UserDetails) {
            UserDetails userDetails = (UserDetails) principal;
            map.put("username", userDetails.getUsername());
        }
        return "index";
    }

    /**
     * 获取当前登录用户信息, 方式2 注入 Authentication
     * @param authentication
     * @return
     */
    @RequestMapping("/user/info")
    @ResponseBody
    public Object userInfo(Authentication authentication) {
        return authentication.getPrincipal();
    }

    /**
     * 获取当前登录用户信息, 方式3 注入 UesrDetails
     * @param userDetails
     * @return
     */
    @RequestMapping("/user/info2")
    @ResponseBody
    public Object userInfo2(@AuthenticationPrincipal UserDetails userDetails) {
        return userDetails;
    }

}
