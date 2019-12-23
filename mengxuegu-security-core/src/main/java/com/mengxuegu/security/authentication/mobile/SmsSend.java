package com.mengxuegu.security.authentication.mobile;

/**
 * 发送短信的接口
 */
public interface SmsSend {
    boolean sendSms(String mobile, String content);
}
