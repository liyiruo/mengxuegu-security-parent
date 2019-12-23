package com.mengxuegu.security.authentication.mobile;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.SpringSecurityCoreVersion;

import java.util.Collection;

public class MobileAuthenticationToken extends AbstractAuthenticationToken {
    private static final long serialVersionUID = SpringSecurityCoreVersion.SERIAL_VERSION_UID;

    private final Object principal;//认证前是手机号，认证后是用户。

    public MobileAuthenticationToken(Object principal) {
        super(null);
        this.principal = principal;//手机号
        setAuthenticated(false);
    }
    /**
     * 当认证通过后,会重新创建一个新的MobileAuthenticationToken,来标识它已经认证通过,
     * @param principal 用户信息
     * @param authorities 用户权限
    */
    public MobileAuthenticationToken(Object principal,
                                     Collection<? extends GrantedAuthority> authorities) {
        super(authorities);
        this.principal = principal;//用户信息
        super.setAuthenticated(true); // must use super, as we override  // 标识已经认证通过
    }

    /**
     * 父类中的抽象方法，所以要实现 返回空即可。
     * @return
     */
    @Override
    public Object getCredentials() {
        return null;
    }

    /**
     * 手机号码
     * @return
     */
    public Object getPrincipal() {
        return this.principal;
    }

    public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
        if (isAuthenticated) {
            throw new IllegalArgumentException(
                    "Cannot set this token to trusted - use constructor which takes a GrantedAuthority list instead");
        }
        super.setAuthenticated(false);
    }

    @Override
    public void eraseCredentials() {
        super.eraseCredentials();
    }
}