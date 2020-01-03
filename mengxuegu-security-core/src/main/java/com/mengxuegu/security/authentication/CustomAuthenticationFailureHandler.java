package com.mengxuegu.security.authentication;

import com.mengxuegu.base.result.MengxueguResult;
import com.mengxuegu.security.properties.LoginResponseType;
import com.mengxuegu.security.properties.SecurityProperties;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 认证失败处理器
 */
@Component("customAuthenticationFailureHandler")
//public class CustomAuthenticationFailureHandler implements AuthenticationFailureHandler {
public class CustomAuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler {
    @Autowired
    private SecurityProperties securityProperties;

    /**
     * 认证失败时抛出的异常
     *
     * @param request
     * @param response
     * @param e
     * @throws IOException
     * @throws ServletException
     */
    @Override
    public void onAuthenticationFailure(
            HttpServletRequest request,
            HttpServletResponse response,
            AuthenticationException e) throws IOException, ServletException {
        if (securityProperties.getAuthentication().getLoginType().equals(LoginResponseType.JSON)) {
            //认证失败状态码401
            MengxueguResult result = MengxueguResult
                    .build(HttpStatus.UNAUTHORIZED.value(),
                            e.getMessage());
            response.setContentType("application/json;charset=UTF-8");
            response.getWriter().write(result.toJsonString());
        } else {
            // super.setDefaultFailureUrl(securityProperties.getAuthentication().getLoginPage()+"?error");
            String referer = request.getHeader("Referer");
            Object toAuthentication = request.getAttribute("toAuthentication");
            logger.info("referer====>{}" + referer);

            String lastUrl =   toAuthentication!=null?securityProperties.getAuthentication().getLoginPage():
                    StringUtils.substringBefore(referer, "?");
            logger.info("上一次请求的路径url路径===》{}" + lastUrl);
            super.setDefaultFailureUrl(lastUrl + "?error");
            super.onAuthenticationFailure(request, response, e);
        }

    }
}
