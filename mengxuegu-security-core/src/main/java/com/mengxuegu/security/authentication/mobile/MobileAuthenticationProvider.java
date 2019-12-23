package com.mengxuegu.security.authentication.mobile;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;


public class MobileAuthenticationProvider implements AuthenticationProvider {

    UserDetailsService userDetailsService;

    /**
     * 处理认证
     * 1.通过手机号去数据库查询信息（UserDetailService）
     * 2.重新构建认证信息
     * @param authentication
     * @return
     * @throws AuthenticationException
     */
    @Override
    public Authentication authenticate(Authentication authentication)
            throws AuthenticationException {
        MobileAuthenticationToken mobileAuthenticationToken = (MobileAuthenticationToken) authentication;
        //获取用户输入的手机号
        String mobile = (String) mobileAuthenticationToken.getPrincipal();
        //查询数据库
        UserDetails userDetails = userDetailsService.loadUserByUsername(mobile);
        if (mobile == null) {
            throw new AuthenticationServiceException("该手机号未注册");
        }
        // 查询到了用户信息, 则认证通过,就重新构建 MobileAuthenticationToken 实例
        MobileAuthenticationToken authenticationToken = new MobileAuthenticationToken(userDetails, userDetails.getAuthorities());
        authenticationToken.setDetails(mobileAuthenticationToken.getDetails());
     return authenticationToken;
    }

    /**
     * 通过此方法判断采用哪一个AuthenticationProvider
     * @param authentication
     * @return
     */
    @Override
    public boolean supports(Class<?> authentication) {
        return MobileAuthenticationToken.class.isAssignableFrom(authentication);
    }

    /**
     *  注入 MobileUserDetailsService
     * @param userDetailsService
     */
    public void setUserDetailsService(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }
}
