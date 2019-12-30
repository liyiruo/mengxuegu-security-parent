package com.mengxuegu.security.authentication.session;

import com.mengxuegu.security.authentication.CustomAuthenticationFailureHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.session.SessionInformationExpiredEvent;
import org.springframework.security.web.session.SessionInformationExpiredStrategy;

import javax.servlet.ServletException;
import java.io.IOException;

/**
 * 当同一用户的登录session值 大于设定值时 会执行该类
 */
public class CustomSessionInformationExpiredStrategy implements SessionInformationExpiredStrategy {

    @Autowired
    CustomAuthenticationFailureHandler customAuthenticationFailureHandler;
    @Override
    public void onExpiredSessionDetected(SessionInformationExpiredEvent event) throws IOException {

        UserDetails principal =(UserDetails) event.getSessionInformation().getPrincipal();
        AuthenticationException exception = new AuthenticationServiceException("[%s]用户在另一台电脑登录，你已被下线");
        try {
            event.getRequest().setAttribute("toAuthentication",true);
            customAuthenticationFailureHandler.onAuthenticationFailure(event.getRequest(),event.getResponse(),exception);
        } catch (ServletException e) {
            e.printStackTrace();
        }
    }
}
