package com.tfkfan.mvc.hibernate.dao.impl;

import javax.transaction.Transactional;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate4.HibernateTemplate;
import org.springframework.stereotype.Component;

import com.tfkfan.mvc.hibernate.dao.IPersonDao;
import com.tfkfan.mvc.hibernate.entities.Person;

@Transactional
public class PersonDao implements IPersonDao {
	
	@Autowired
	HibernateTemplate hibernateTemplate;

	public PersonDao() {
	}

	public void savePerson() {
		Person person = new Person();
		person.setName("Ram2");
		hibernateTemplate.save(person);
	}
}