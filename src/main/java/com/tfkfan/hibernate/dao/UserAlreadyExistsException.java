package com.tfkfan.hibernate.dao;

public class UserAlreadyExistsException extends Exception {
	private static final long serialVersionUID = 1L;

	public UserAlreadyExistsException() {
		super();
	}

	public UserAlreadyExistsException(String username) {
		super("User already exists with username: '" + username + "'");
	}
}
