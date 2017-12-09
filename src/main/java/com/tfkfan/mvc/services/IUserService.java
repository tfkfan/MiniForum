package com.tfkfan.mvc.services;

import com.tfkfan.hibernate.entities.User;

public interface IUserService<T extends User> {
	void updateUser(T user);
	void addUser(T user);
	T getUserById(Integer id);
	T getUserByName(String userName);
	void deleteUser(Integer id);
	
}
