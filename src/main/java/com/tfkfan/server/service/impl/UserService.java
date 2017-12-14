package com.tfkfan.server.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.tfkfan.hibernate.dao.RoleDao;
import com.tfkfan.hibernate.dao.UserDao;
import com.tfkfan.hibernate.entities.Role;
import com.tfkfan.hibernate.entities.User;
import com.tfkfan.security.enums.UserRole;
import com.tfkfan.server.service.IService;
import com.tfkfan.server.service.exception.IncorrectUsernameException;
import com.tfkfan.server.service.exception.UsernameAlreadyExistsException;

@Service("userService")
public class UserService implements IService<User> {

	@Autowired
	UserDao userDao;

	@Autowired
	PasswordEncoder pe;

	@Autowired
	RoleDao roleDao;

	Role userRole;

	@Autowired
	public void initRole() {
		userRole = roleDao.getRoleByName(UserRole.ROLE_USER.getRole());
	}

	@Override
	public void update(User obj) throws Exception {
		userDao.update(obj);
	}

	@Override
	public void add(User obj) throws Exception {
		if (obj.getUsername() == null || obj.getUsername().isEmpty())
			throw new IncorrectUsernameException();
		User user = userDao.findByUsername(obj.getUsername());
		if (user != null)
			throw new UsernameAlreadyExistsException(obj.getUsername());
		userDao.save(obj);
	}

	public void createNewUser(String username, String password) throws Exception {
		User user = new User();

		password = pe.encode(password);

		user.setUsername(username);
		user.setPassword(password);
		user.setRole(userRole);

		add(user);
	}

	@Override
	public void delete(User obj) throws Exception {
		userDao.delete(obj);
	}

	@Override
	public User get(Long id) throws Exception {
		if(id == null)
			throw new NullPointerException("Id is null");
		return userDao.get(id);
	}

	@Override
	public List<User> getAll() throws Exception {
		return userDao.listAll();
	}

}
