package com.mengxuegu.security.authentication.session;

import com.mengxuegu.base.result.MengxueguResult;
import org.springframework.boot.web.servlet.server.Session;
import org.springframework.http.HttpStatus;
import org.springframework.security.web.session.InvalidSessionStrategy;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * session 失效后的处理逻辑
 */
public class CustomInvalidSessionStrategy implements InvalidSessionStrategy {
    @Override
    public void onInvalidSessionDetected(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // 将浏览器的sessionid清除，不关闭浏览器cookie不会被删除，一直请求都提示：Session失效
        cancelCookie(request, response);
        MengxueguResult result = new MengxueguResult().build(
                HttpStatus.UNAUTHORIZED.value(), "CustomInvalidSessionStrategy=>登录已超时，请重新登录"
        );
        //以JSON的格式转回去
        response.setContentType("application/Json;charset=UTF-8");
        response.getWriter().write(result.toJsonString());
    }

    protected void cancelCookie(HttpServletRequest request, HttpServletResponse response) {
        Cookie cookie = new Cookie("JSESSIONID", (String) null);
        cookie.setMaxAge(0);
        cookie.setPath(this.getCookiePath(request));
        response.addCookie(cookie);
    }


    private String getCookiePath(HttpServletRequest request) {
        String contextPath = request.getContextPath();
        return contextPath.length() > 0 ? contextPath : "/";
    }
}
