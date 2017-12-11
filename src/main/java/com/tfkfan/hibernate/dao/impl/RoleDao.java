package com.tfkfan.hibernate.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.tfkfan.hibernate.entities.Role;
import com.tfkfan.hibernate.entities.User;

@Component("roleDao")
@Scope("singleton")
@Transactional
public class RoleDao {
	@Autowired
	HibernateTemplate t;
  
	
	public RoleDao() {
	
	}

	public void save(Role object) {
		t.save(object);
	}
	
	public Role getRoleByName(String role) {
		return (Role) t.find("FROM Role WHERE role=?" , role).get(0);
	}
}
