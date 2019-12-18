package com.mengxuegu.security.authentication.code;

import com.mengxuegu.security.authentication.CustomAuthenticationFailureHandler;
import com.mengxuegu.security.controller.CustomLoginController;
import com.mengxuegu.security.properties.SecurityProperties;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.ValidationException;
import java.io.IOException;

/**
 * 所有请求之前被调用一次
 */
@Slf4j
@Component("imageCodeValidateFilter")
public class ImageCodeValidateFilter extends OncePerRequestFilter {

    @Autowired
    SecurityProperties securityProperties;
    @Autowired
    CustomAuthenticationFailureHandler customAuthenticationFailureHandler;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // 1. 如果是post方式 的登录请求，则校验输入的验证码是否正确
        log.info("进入==》ImageCodeValidateFilter.doFilterInternal方法");
        log.info("登录表单提交处理Url==》{}",securityProperties.getAuthentication().getLoginProcessingUrl());
        log.info("请求路径=>{}",request.getRequestURI());
        log.info("请求方式=>{}",request.getMethod());
        log.info("boolen====>{}",securityProperties
                .getAuthentication()
                .getLoginProcessingUrl()
                .equals(request.getRequestURI())
                && request.getMethod().equalsIgnoreCase("POST"));

        if (securityProperties
                .getAuthentication()
                .getLoginProcessingUrl()
                .equals(request.getRequestURI())
                && request.getMethod().equalsIgnoreCase("POST")) {
            log.info("进入判断+是post方式 +请求路径符合");
            try {
                //校验验证码的合法性
                validate(request);
            } catch (AuthenticationException e) {
                customAuthenticationFailureHandler.onAuthenticationFailure(request, response, e);
                return;
            }

        }
        log.info("出判断if外");
        //放行请求
        filterChain.doFilter(request, response);
    }

    private void validate(HttpServletRequest request) {
        //获取SESSION中的验证码
        String sessionCode = (String) request.getSession().getAttribute(CustomLoginController.SESSION_KEY);
        //获取用户输入的验证码
        String inputcode = request.getParameter("code");
        //判断是否正确
        if (StringUtils.isBlank(inputcode)) {
            throw new ValidateCodeException("验证码不能为空");
        }
        if (!inputcode.equalsIgnoreCase(sessionCode)) {
            throw new ValidateCodeException("验证码不正确");
        }
    }
}
