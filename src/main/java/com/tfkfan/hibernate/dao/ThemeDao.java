package com.tfkfan.hibernate.dao;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import com.tfkfan.hibernate.entities.Theme;

@Component("themeDao")
@Scope("singleton")
@Transactional
public class ThemeDao extends AbstractDao<Theme> {
	
	public ThemeDao() {
		super(Theme.class);
	}
}
