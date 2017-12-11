package com.basakpie.security;


import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;


@Component("userDao")
@Scope("singleton")
@Transactional
public class UserDao  {
	@Autowired
	private SessionFactory sessionFactory;

	@Autowired
	HibernateTemplate t;
  
	
	public UserDao() {
	
	}

	public void save(User object) {
		t.save(object);
	}
	
	public User findByUsername(String username){
		return (User) t.find("FROM User WHERE username=?", username).get(0);
				
	}
	
	public List<User> findAll(){
		return (List<User>) t.find("FROM User");
	}
}
