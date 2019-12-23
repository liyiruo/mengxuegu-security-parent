package com.mengxuegu.security.authentication.mobile;

import com.mengxuegu.security.authentication.CustomAuthenticationFailureHandler;
import com.mengxuegu.security.authentication.exception.ValidateCodeExcetipn;
import com.mengxuegu.security.controller.CustomMobileController;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;


import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 校验用户输入的手机验证码是否正确
 */
@Component
public class MobileValidateFilter extends OncePerRequestFilter {
    @Autowired
    CustomAuthenticationFailureHandler customAuthenticationFailureHandler;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain) throws ServletException, IOException {
        if ("/mobile/form".equals(request.getRequestURI())
                && "POST".equalsIgnoreCase(request.getMethod())) {
            try {
                // 校验验证码合法性
                validate(request);
            } catch (AuthenticationException e) {
                // 交给失败处理器进行处理异常
                customAuthenticationFailureHandler.onAuthenticationFailure(request, response, e);
                // 一定要记得结束
                return;
            }
        }
        filterChain.doFilter(request, response);
    }

    private void validate(HttpServletRequest request) {
        // 先获取seesion中的验证码
        String sessionCode = (String) request.getSession().getAttribute(CustomMobileController.SESSION_KEY);
        // 获取用户输入的验证码
        String inputCode = request.getParameter("code");
        // 判断是否正确
        if (StringUtils.isBlank(inputCode)) {
            throw new ValidateCodeExcetipn.ValidateCodeException("短信验证码不能为空");
        }
        //是这里错了吗？是这里错了，少写了一个"!"
        if (!inputCode.equalsIgnoreCase(sessionCode)) {
            throw new ValidateCodeExcetipn.ValidateCodeException("短信验证码不正确");
        }
    }


}
