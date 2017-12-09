package com.tfkfan.hibernate.dao;

import java.util.List;

import org.springframework.stereotype.Component;

public interface IDao<T extends Object> {
	public void save(T obj);
	public T get(Integer id);
	public List<T> getList();
}