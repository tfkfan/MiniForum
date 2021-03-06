package com.tfkfan.server.service.impl;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import org.springframework.stereotype.Service;

import com.tfkfan.hibernate.dao.ThemeDao;
import com.tfkfan.hibernate.entities.Theme;
import com.tfkfan.hibernate.entities.User;

@Service("themeService")
public class ThemeService extends AbstractService<Theme> {

	public ThemeService() {

	}

	public void addTheme(String themeTitle, User user) throws Exception {
		Theme theme = new Theme(Date.from(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant()), themeTitle,
				user);
		add(theme);
	}

	public List<Theme> getSortedThemes() throws Exception {
		return ((ThemeDao) getDao()).getSortedThemes();
	}
}
