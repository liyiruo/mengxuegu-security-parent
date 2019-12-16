package com.mengxuegu.security.config;

import com.mengxuegu.security.properties.SecurityProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * 安全控制中心
 */
@Configuration
@EnableWebSecurity //启动 SpringSecurity 过滤器链功能
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private SecurityProperties securityProperties;
    Logger logger = LoggerFactory.getLogger(getClass());

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /*PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    PasswordEncoder passwordEncoder1 = NoOpPasswordEncoder.getInstance();*/
    /**
     * 身份认证管理器：
     * 认证信息提供方式（用户名、密码、当前用户的资源权限）
     * 可采用内存存储方式，也可能采用数据库方式等
     *
     * @param auth
     * @throws Exception
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        // 用户信息存储在内存中
        String password = passwordEncoder().encode("root");
        logger.info("加密之后存储的密码：" + password);

        // super.configure(auth);
        auth.inMemoryAuthentication()
                .withUser("root")
                .password(password)
                .authorities("ADMIN");
    }

    /**
     * 资源权限配置（过滤器链)
     * 拦截的哪一些资源
     * 资源所对应的角色权限
     * 定义认证方式： httpBasic 、 httpForm
     * 定制登录页面、登录请求地址、错误处理方式
     * 自定义 spring security 过滤器等
     *
     * @param http
     * @throws Exception
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //super.configure(http);
        //http.httpBasic()
        http.formLogin()// 表单认证
                .loginPage(securityProperties.getAuthentication().getLoginPage())// 交给 /login/page 响应认证(登录)页面
                .loginProcessingUrl(securityProperties.getAuthentication().getLoginProcessingUrl())// 登录表单提交处理Url, 默认是 /login
                .usernameParameter(securityProperties.getAuthentication().getUsernameParameter())// 默认用户名的属性名是 username
                .passwordParameter(securityProperties.getAuthentication().getPasswordParameter())// 默认密码的属性名是 password
                .and()
                .authorizeRequests()//认证请求
                .antMatchers(securityProperties.getAuthentication().getLoginPage()).permitAll()// 放行跳转认证请求
                .anyRequest().authenticated()// 所有进入应用的HTTP请求都要进行认证
        ;
    }

    /**
     * 一般用来放行静态资源
     * @param web
     */
    @Override
    public void configure(WebSecurity web) {
        //super.configure(web);
        web.ignoring().antMatchers(securityProperties.getAuthentication().getStaticPaths());
    }

}
