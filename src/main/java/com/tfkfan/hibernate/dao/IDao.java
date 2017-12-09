package com.tfkfan.hibernate.dao;

import java.util.List;
public interface IDao<T extends Object> {
	public void save(T obj);
	public T get(Integer id);
	public List<T> getList();
}