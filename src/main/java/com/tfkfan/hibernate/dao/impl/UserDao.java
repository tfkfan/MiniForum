package com.tfkfan.hibernate.dao.impl;

import java.util.List;

import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.orm.hibernate4.HibernateTemplate;
import org.springframework.stereotype.Component;

import com.tfkfan.hibernate.dao.IDao;
import com.tfkfan.hibernate.entities.User;
@Component
@Scope("singleton")
@Transactional
public class UserDao implements IDao<User> {

	@Autowired
	HibernateTemplate hibernateTemplate;

	public UserDao() {
	}

	@Override
	public void save(User user) {
		hibernateTemplate.save(user);
	}

	@Override
	public User get(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<User> getList() {
		// TODO Auto-generated method stub
		return null;
	}
}