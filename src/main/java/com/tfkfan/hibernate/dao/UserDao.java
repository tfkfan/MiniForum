package com.tfkfan.hibernate.dao;

import java.util.List;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import com.tfkfan.hibernate.entities.User;

@Component("userDao")
@Scope("singleton")
@Transactional
public class UserDao extends AbstractDao<User> {

	public UserDao() {
		super(User.class);

	}

	public User findByUsername(String username) {
		List<User> users = this.listAllWithParam(String.format("username='%s'", username));
		if (users.isEmpty())
			return null;
		return users.get(0);
	}

	public void createUser(User user) throws UserAlreadyExistsException {
		User foundUser = findByUsername(user.getUsername());
		if (foundUser != null)
			throw new UserAlreadyExistsException(user.getUsername());

		save(user);
	}
}
