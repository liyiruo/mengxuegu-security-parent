package com.mengxuegu.security.authentication.exception;

import org.springframework.security.core.AuthenticationException;

public class ValidateCodeExcetipn extends AuthenticationException {
    public ValidateCodeExcetipn(String msg, Throwable t) {
        super(msg, t);
    }

    public ValidateCodeExcetipn(String msg) {
        super(msg);
    }

    /**
     * 验证码相关异常类
     */
    public static class ValidateCodeException extends AuthenticationException {

        public ValidateCodeException(String msg, Throwable t) {
            super(msg, t);
        }

        public ValidateCodeException(String msg) {
            super(msg);
        }

    }
}
