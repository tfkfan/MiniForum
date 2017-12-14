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

import static com.tfkfan.server.ServerUtils.LOGIN_PAGE;
import static com.tfkfan.server.ServerUtils.SIGNUP_PAGE;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javax.ws.rs.core.MediaType;

@RestController
@RequestMapping("/themes")
public class ThemesController {
	private final static Logger log = Logger.getLogger(ThemesController.class.getName());

	@Autowired
	UserDao userDao;

	@Autowired
	ThemeDao themeDao;

	@RequestMapping(method = RequestMethod.GET)
	public List<ThemeDto> themes() {
		List<Theme> tms = themeDao.listAll();
		List<ThemeDto> resp = new ArrayList<ThemeDto>();
		for (Theme t : tms)
			resp.add(new ThemeDto(t.getId(), t.getTitle(), t.getDate(), t.getAutor().getUsername()));
		return resp;
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ThemeDto theme(@PathVariable Long id) {
		Theme t = themeDao.get(id);
		if(t != null) {
			ThemeDto resp = new ThemeDto(id, t.getTitle(), t.getDate(), t.getAutor().getUsername());
			return resp;
		}
		return null;
	}

	@RequestMapping(value = "/add", method = RequestMethod.PUT)
	public void addTheme(@RequestBody ThemeDto themeDto) {
		log.info(themeDto.getAutor());
		User user = userDao.findByUsername(themeDto.getAutor());
		if (user != null) {
			log.info("!" + themeDto.getAutor());
			Theme theme = new Theme(LocalDateTime.now().toString(), themeDto.getTitle(), user);
			themeDao.save(theme);
		}
	}

}
