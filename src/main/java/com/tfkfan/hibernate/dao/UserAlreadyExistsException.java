package com.tfkfan.hibernate.dao;

import org.springframework.security.core.AuthenticationException;

public class UserAlreadyExistsException extends AuthenticationException {
	private static final long serialVersionUID = 1L;

	public UserAlreadyExistsException(String username) {
		super("Incorrect username");
	}
}
