package com.mengxuegu.security.authentication;

import com.alibaba.fastjson.JSON;
import com.mengxuegu.base.result.MengxueguResult;
import com.mengxuegu.security.properties.LoginResponseType;
import com.mengxuegu.security.properties.SecurityProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component("customAuthenticationSuccessHandler")
//public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
public class CustomAuthenticationSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {
    Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private SecurityProperties securityProperties;

    /**
     * 认证成功后的处理逻辑
     *
     * @param request
     * @param response
     * @param authentication
     * @throws IOException
     * @throws ServletException
     */
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {

        /**
         * LoginResponseType.JSON 与 配置文件是否一致
         * 如果一致 则返回JSON串
         *
         */
        if (LoginResponseType.JSON.equals(securityProperties.getAuthentication().getLoginType())) {
            // 当认证成功后，响应 JSON 数据给前端
            MengxueguResult result = MengxueguResult.ok();
            response.setContentType("application/json;charset=UTF-8");
            response.getWriter().write(result.toJsonString());
        } else {
            logger.info("authentication{}",JSON.toJSONString(authentication));
            //重新定向到上次请求的地址上。
            super.onAuthenticationSuccess(request, response, authentication);
        }

    }


}
