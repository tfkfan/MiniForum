package com.tfkfan.server.service.exception;

public class UsernameAlreadyExistsException extends Exception {

	private static final long serialVersionUID = 1L;

	public UsernameAlreadyExistsException(String username){
		super("Username already exists: '" + username + "'");
	}
}
