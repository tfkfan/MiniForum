package com.tfkfan.hibernate.dao;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public abstract class AbstractDao<T> {

	@Autowired
	protected HibernateTemplate hTemplate;

	protected final Class<T> clazz;

	public AbstractDao(Class<T> clazz) {
		this.clazz = clazz;
	}

	public void save(T object) {
		hTemplate.save(object);
	}

	public T get(Long id) {
		return hTemplate.get(clazz, id);
	}

	public List<T> listAll() {
		return hTemplate.loadAll(clazz);
	}
	
	public void update(T obj){
		hTemplate.merge(obj);
	}

	protected HibernateTemplate getTemplate() {
		return hTemplate;
	}

	protected void setTemplate(HibernateTemplate hTemplate) {
		this.hTemplate = hTemplate;
	}
	
}
