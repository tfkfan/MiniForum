package com.tfkfan.server.service.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.tfkfan.security.enums.UserRole;

@JsonIgnoreProperties(ignoreUnknown = true)
public class UserDto extends Dto {
	private static final long serialVersionUID = 1L;
	
	protected String username;
	protected String password;
	protected String role;
	
	public UserDto() {
		
	}
}
