package com.tfkfan.server.service.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class UserDto extends Dto {
	private static final long serialVersionUID = 1L;

	protected String username;
	protected String password;
	protected String role;

	public UserDto() {

	}

	public UserDto(Long id, String username, String password, String role) {
		setId(id);
		setUsername(username);
		setPassword(password);
		setRole(role);
	}
	
	public UserDto(String username, String password, String role) {
		setUsername(username);
		setPassword(password);
		setRole(role);
	}
	
	public UserDto(String username, String password) {
		setUsername(username);
		setPassword(password);
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}
}
