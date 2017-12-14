package com.tfkfan.server.service.exception;

public class IncorrectUsernameException extends Exception {

	private static final long serialVersionUID = 1L;

	public IncorrectUsernameException(){
		super("Incorrect username. Username is null or empty");
	}
}
