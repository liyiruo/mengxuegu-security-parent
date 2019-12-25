package com.mengxuegu.security.properties;

import lombok.Data;

@Data
public class AuthenticationProperties {

    // application.yml 没配置取默认值
    private String loginPage = "/login/page";
    private String loginProcessingUrl = "/login/form";
    private String usernameParameter = "name";
    private String passwordParameter = "pwd";
    private String[] staticPaths = {"/dist/**", "/modules/**", "/plugins/**"};
    /**
     * 登录成功后响应 JSON , 还是重定向
     * 如果application.yml 中没有配置，则取此初始值 REDIRECT
     * 即为默认值
     */
    private LoginResponseType loginType = LoginResponseType.REDIRECT;
    private String imageCodeUrl = "/code/image";
    private String mobileCodeUrl="/mobile/page";
    private String mobilePageUrl = "/code/mobile";
    private Integer tokenValiditySeconds = 60 * 60 * 24 * 7;

    /**
     * imageCodeUrl: /code/image #获取图形验证码
     *       mobileCodeUrl: /mobile/page #发送手机验证码地址
     *       mobilePageUrl: /code/mobile #前往手机登录界面
     *       tokenValiditySeconds: 604800
     */




}
