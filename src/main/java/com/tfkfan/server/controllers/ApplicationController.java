package com.tfkfan.server.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.tfkfan.hibernate.dao.AbstractDao;
import com.tfkfan.hibernate.dao.MessageDao;
import com.tfkfan.hibernate.dao.ThemeDao;
import com.tfkfan.hibernate.dao.UserDao;
import com.tfkfan.hibernate.entities.Message;
import com.tfkfan.hibernate.entities.Theme;
import com.tfkfan.hibernate.entities.User;
import com.tfkfan.server.service.dto.ThemeDto;

import static com.tfkfan.server.ServerUtils.LOGIN_PAGE;
import static com.tfkfan.server.ServerUtils.SIGNUP_PAGE;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

@RestController
@RequestMapping("/themes")
public class ApplicationController {
	private final static Logger log = Logger.getLogger(ApplicationController.class.getName());

	@Autowired
	UserDao userDao;

	@Autowired
	ThemeDao themeDao;


	@RequestMapping(method = RequestMethod.GET, produces = "application/json")
	public List<ThemeDto> themes() {
		List<Theme> tms = themeDao.listAll();
		List<ThemeDto> resp = new ArrayList<ThemeDto>();
		for (Theme t : tms)
			resp.add(new ThemeDto(t.getId(), t.getTitle(), t.getDate(), t.getAutor().getUsername()));
		return resp;
	}

	@RequestMapping(value = "/put", method = RequestMethod.PUT, produces = "application/json")
	public ThemeDto addTheme(@RequestBody ThemeDto themeDto) {
		User user = userDao.findByUsername(themeDto.getAutor());
		if (user != null) {
			Theme theme = new Theme(LocalDateTime.now().toString(), themeDto.getTitle(), user);
			themeDao.save(theme);
		}
		return themeDto;
	}

	
}
