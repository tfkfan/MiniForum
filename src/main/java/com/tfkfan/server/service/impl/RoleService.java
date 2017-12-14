package com.tfkfan.server.service.impl;


import org.springframework.stereotype.Service;
import com.tfkfan.hibernate.dao.RoleDao;
import com.tfkfan.hibernate.entities.Role;

@Service("roleService")
public class RoleService extends AbstractService<Role> {

	public RoleService() {

	}
	
	public Role getByRole(String role){
		return ((RoleDao)getDao()).getRoleByName(role);
	}
}
