package com.mengxuegu.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Slf4j
@Component("mobileUserDetailsService")//是这的原因吗？
public class MobileUserDetailsService implements UserDetailsService {

    @Override
    public UserDetails loadUserByUsername(String mobile) throws UsernameNotFoundException {
        log.info("请求的手机号是{}", mobile);
        //1.通过手机号查询用户信息:

         mobile = "meng";
        //2.如果有此用户，则查询用户权限
        String auth = "ADMIN";
        //3.封装用户信息
        /**
         * private String password;
         * 	private final String username;
         * 	private final Set<GrantedAuthority> authorities;
         * 	private final boolean accountNonExpired;
         * 	private final boolean accountNonLocked;
         * 	private final boolean credentialsNonExpired;
         * 	private final boolean enabled;
         */
        return new User(
                mobile,
                "",
                true,
                true,
                true,
                true,
                AuthorityUtils.commaSeparatedStringToAuthorityList(auth));
    }
}
