package com.tfkfan.hibernate.dao;

import java.io.Serializable;
import java.util.List;
public interface IDao<T> extends Serializable{
	public void save(T obj);
	public T get(Integer id);
	public List<T> getList();
}