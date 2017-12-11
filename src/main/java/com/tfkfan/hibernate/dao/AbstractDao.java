package com.tfkfan.hibernate.dao;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.HibernateTemplate;

public abstract class AbstractDao<T> {
	@Autowired
	protected HibernateTemplate hTemplate;
	
	protected final Class<T> clazz;
  
	public AbstractDao(Class<T> clazz){
		this.clazz = clazz;
	}
	
	public void save(T object) {
		hTemplate.save(object);
	}
	
	public T get(Long id){
		return hTemplate.get(clazz, id);
	}
	
	public List<T> listAll(){
		return hTemplate.loadAll(clazz);
	}
	
	protected HibernateTemplate getTemplate(){
		return hTemplate;
	}
}
