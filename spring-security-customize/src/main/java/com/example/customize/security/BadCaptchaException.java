package com.example.customize.security;

import org.springframework.security.core.AuthenticationException;

// 自定义异常，继承自 AuthenticationException
public class BadCaptchaException extends AuthenticationException {

	private static final long serialVersionUID = 1L;

	public BadCaptchaException(String msg) {
		super(msg);
	}

	public BadCaptchaException(String msg, Throwable t) {
		super(msg, t);
	}

}
