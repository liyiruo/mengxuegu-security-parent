package com.mengxuegu.security.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@ConfigurationProperties(prefix = "mengxuegu.security")
@Component
public class SecurityProperties {

    private AuthenticationProperties authentication;

    public AuthenticationProperties getAuthentication() {

        return authentication;
    }

    public void setAuthentication(AuthenticationProperties authentication) {
        this.authentication = authentication;
    }
}
