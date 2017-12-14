package com.tfkfan.server.service.impl;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tfkfan.hibernate.dao.ThemeDao;
import com.tfkfan.hibernate.entities.Theme;
import com.tfkfan.hibernate.entities.User;
import com.tfkfan.server.service.IService;

@Service("themeService")
public class ThemeService implements IService<Theme> {

	@Autowired
	ThemeDao themeDao;

	@Override
	public void update(Theme obj) throws Exception {
		themeDao.update(obj);
	}

	@Override
	public void add(Theme obj) throws Exception {
		themeDao.save(obj);
	}
	
	public void addTheme(String themeTitle, User user) throws Exception{
		Theme theme = new Theme(LocalDateTime.now().toString(), themeTitle, user);
		add(theme);
	}

	@Override
	public void delete(Theme obj) throws Exception {
		themeDao.delete(obj);
	}

	@Override
	public Theme get(Long id) throws Exception {
		if(id == null)
			throw new NullPointerException("Id is null");
		return themeDao.get(id);
	}

	@Override
	public List<Theme> getAll() throws Exception {
		return themeDao.listAll();
	}

}
