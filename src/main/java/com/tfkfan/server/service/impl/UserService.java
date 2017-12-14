package com.tfkfan.server.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.tfkfan.hibernate.dao.RoleDao;
import com.tfkfan.hibernate.dao.UserDao;
import com.tfkfan.hibernate.entities.Role;
import com.tfkfan.hibernate.entities.User;
import com.tfkfan.security.enums.UserRole;

@Service("userService")
public class UserService extends AbstractService<User> {

	@Autowired
	PasswordEncoder pe;

	@Autowired
	RoleDao roleDao;

	Role userRole;

	@Autowired
	public void initRole() {
		userRole = roleDao.getRoleByName(UserRole.ROLE_USER.getRole());
	}
	
	public UserService(){
		
	}

	public void createNewUser(String username, String password) throws Exception {
		User user = new User();

		password = pe.encode(password);

		user.setUsername(username);
		user.setPassword(password);
		user.setRole(userRole);

		((UserDao)getDao()).createUser(user);
	}
}
