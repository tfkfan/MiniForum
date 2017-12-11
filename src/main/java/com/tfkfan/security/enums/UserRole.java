package com.tfkfan.security.enums;

public enum UserRole {
	 ROLE_ADMIN("ROLE_ADMIN"),
	    ROLE_USER("ROLE_USER");
	
	String role;
	
	UserRole(String role) {
		this.role = role;
	}
	
	public String getRole() {
		return role;
	}
	
	public static UserRole findByRole(String role) {
        for(UserRole roleObj: values()) {
            if(roleObj.getRole().equals(role))
                return roleObj;
        }
        return null;
    }
}
