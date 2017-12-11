package com.tfkfan.hibernate.dao;

import java.util.List;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.orm.hibernate5.HibernateTemplate;
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
		return (User) getTemplate().find("FROM User WHERE username=?", username).get(0);

	}
}
