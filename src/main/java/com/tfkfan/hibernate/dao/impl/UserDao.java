package com.tfkfan.hibernate.dao.impl;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import com.tfkfan.hibernate.entities.User;

@Component
@Scope("singleton")
@Transactional
@Qualifier("userDao") 
public class UserDao extends AbstractDao<User> {
	private static final long serialVersionUID = 1L;

	public UserDao() {
		super(User.class);
	}

	public User findByUserName(String username) {
		return (User) getTemplate().find("SELECT * FROM Users WHERE name=?", username).get(0);
	}
}