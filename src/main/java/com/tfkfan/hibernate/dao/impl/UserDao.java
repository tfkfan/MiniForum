package com.tfkfan.hibernate.dao.impl;


import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.tfkfan.hibernate.entities.User;

@Component("userDao")
@Scope("singleton")
@Transactional(readOnly = false)
public class UserDao  {
	@Autowired
	private SessionFactory sessionFactory;

  
	
	public UserDao() {
	
	}

	public void save(User object) {
		Session session = this.sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		session.persist(object);
		tx.commit();
		session.close();
	}
}
