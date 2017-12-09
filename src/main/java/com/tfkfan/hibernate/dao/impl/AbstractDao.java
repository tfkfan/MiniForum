package com.tfkfan.hibernate.dao.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate4.HibernateTemplate;
import com.tfkfan.hibernate.dao.IDao;

public abstract class AbstractDao<T> implements IDao<T> {
	private static final long serialVersionUID = 1L;

	private final Class<T> clazz;

	@Autowired
	private HibernateTemplate hibernateTemplate;

	public AbstractDao(Class<T> clazz) {
		this.clazz = clazz;
	}

	@Override
	public void save(T object) {
		hibernateTemplate.save(object);
	}

	@Override
	public T get(Integer id) {
		return hibernateTemplate.get(clazz, id);
	}

	@Override
	public List<T> getList() {
		return hibernateTemplate.loadAll(clazz);
	}
	
	protected HibernateTemplate getTemplate(){
		return hibernateTemplate;
	}
}
