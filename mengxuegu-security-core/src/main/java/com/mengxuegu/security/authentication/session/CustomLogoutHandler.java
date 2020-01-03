package com.mengxuegu.security.authentication.session;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class CustomLogoutHandler implements LogoutHandler {
    @Autowired
    private SessionRegistry sessionRegistry;
    @Override
    public void logout(HttpServletRequest httpServletRequest,
                       HttpServletResponse httpServletResponse,
                       Authentication authentication) {
        // 配置 .maxSessionsPreventsLogin(true) 开启了前面已登录，不允许再重复登录.
        // 默认情况，如果登录后，然后请求 /logout 退出，再重新登录时，会提示不能重复登录。
        // 清除session信息，原因是并没有从 SessionRegistryImpl.principals 移除用户信息
        sessionRegistry.removeSessionInformation(httpServletRequest.getSession().getId());

    }
}
