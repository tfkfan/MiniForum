package com.tfkfan.server.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.tfkfan.hibernate.dao.AbstractDao;
import com.tfkfan.server.service.IService;

public class AbstractService<T> implements IService<T> {

	@Autowired
	AbstractDao<T> dao;
	
	@Override
	public void update(T obj) throws Exception {
		dao.update(obj);
	}

	@Override
	public void add(T obj) throws Exception {
		dao.save(obj);
	}

	@Override
	public void delete(T obj) throws Exception {
		dao.delete(obj);
	}

	@Override
	public T get(Long id) throws Exception {
		if(id == null)
			throw new NullPointerException("Id is null");
		return dao.get(id);
	}

	@Override
	public List<T> getAll() throws Exception {
		return dao.listAll();
	}
	
	public AbstractDao<T> getDao(){
		return dao;
	}

}
