package com.tfkfan.hibernate.dao;

import com.tfkfan.hibernate.entities.User;

public interface IUserDao<F extends User> extends IDao<F>{
	F findByUserName(String username);
}
