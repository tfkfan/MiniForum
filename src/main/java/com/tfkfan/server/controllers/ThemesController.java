package com.tfkfan.server.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
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
import com.tfkfan.server.service.impl.ThemeService;

import static com.tfkfan.server.ServerUtils.LOGIN_PAGE;
import static com.tfkfan.server.ServerUtils.SIGNUP_PAGE;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;


@RestController
@RequestMapping("/themes")
public class ThemesController {
	private final static Logger log = Logger.getLogger(ThemesController.class.getName());
	
	@Autowired
	ThemeService themeService;

	@RequestMapping(method = RequestMethod.GET)
	public List<ThemeDto> themes() throws Exception {
		return themeService.listAllDTO();
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ThemeDto theme(@PathVariable Long id) throws Exception {
		return themeService.getDTO(id);
	}

	@RequestMapping(value = "/add", method = RequestMethod.PUT)
	public void addTheme(@RequestBody ThemeDto themeDto) throws Exception {
		themeService.addTheme(themeDto);
	}

}
