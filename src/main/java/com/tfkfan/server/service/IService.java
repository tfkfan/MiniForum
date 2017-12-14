package com.tfkfan.server.service;

import java.util.List;

public interface IService<T> {
	void update(T obj) throws Exception;
	void add(T obj) throws Exception;
	void delete(T obj) throws Exception;
	T get(Long id) throws Exception;
	List<T> getAll() throws Exception;
}
