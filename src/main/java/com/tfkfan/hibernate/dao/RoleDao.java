package com.tfkfan.hibernate.dao;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import com.tfkfan.hibernate.entities.Role;

@Component("roleDao")
@Scope("singleton")
@Transactional
public class RoleDao extends AbstractDao<Role>{

	public RoleDao() {
		super(Role.class);
		
	}

	public Role getRoleByName(String role) {
		return (Role) getTemplate().find("FROM Role WHERE role=?" , role).get(0);
	}
	
	
}
