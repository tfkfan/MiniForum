package com.tfkfan.security;

import org.springframework.security.core.AuthenticationException;

public class SecurityContextNotFoundException extends AuthenticationException {
	private static final long serialVersionUID = 1L;

	public SecurityContextNotFoundException(String msg, Throwable t) {
        super(msg, t);
    }

    public SecurityContextNotFoundException(String msg) {
        super(msg);
    }
}
