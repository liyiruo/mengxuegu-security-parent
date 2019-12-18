package com.mengxuegu.security.controller;

import com.google.code.kaptcha.impl.DefaultKaptcha;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * 登录处理
 */
@Slf4j
@Controller
public class CustomLoginController {

    Logger logger = LoggerFactory.getLogger(getClass());
    //    public static final String SESSION_KEY = "SESSION_KEY_IMAGE_CODE";
    public static final String SESSION_KEY = "SESSION_KEY_IMAGE_CODE";

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
        log.info("进入login/page请求");
        return "login";
    }

    @Autowired
    private DefaultKaptcha defaultKaptcha;

    /**
     * 获取图像验证码
     *
     * @param request
     * @param response
     */
    @RequestMapping("/code/image")
    public void imageCode(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //1.获取验证码字符串
        String code = defaultKaptcha.createText();
        logger.info("生成的验证码为：{}", code);
        //2.把字符串存到session 中
        request.getSession().setAttribute(SESSION_KEY, code);
        //3.获取验证码图片
        BufferedImage image = defaultKaptcha.createImage(code);
        //4. 将验证码图片把它写出去
        ServletOutputStream out = response.getOutputStream();

        ImageIO.write(image, "jpg", out);
    }
}
