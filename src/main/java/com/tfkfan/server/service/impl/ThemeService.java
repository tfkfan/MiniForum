package com.tfkfan.server.service.impl;

import java.time.LocalDateTime;
import org.springframework.stereotype.Service;
import com.tfkfan.hibernate.entities.Theme;
import com.tfkfan.hibernate.entities.User;

@Service("themeService")
public class ThemeService extends AbstractService<Theme> {

	public ThemeService(){
		
	}
	
	public void addTheme(String themeTitle, User user) throws Exception{
		Theme theme = new Theme(LocalDateTime.now().toString(), themeTitle, user);
		add(theme);
	}

}
