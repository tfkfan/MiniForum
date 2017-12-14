package com.tfkfan.server.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.tfkfan.hibernate.dao.UserDao;
import com.tfkfan.hibernate.entities.User;
import com.tfkfan.server.service.dto.UserDto;

@RestController
@RequestMapping("/_admin")
public class AdminController {
	private final static Logger log = Logger.getLogger(AdminController.class.getName());
	@Autowired
	UserDao userDao;
	
	@Autowired
	PasswordEncoder passwordEncoder;

	//private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-mm HH:mm:ss");

	@RequestMapping(method = RequestMethod.GET)
	public List<UserDto> users() {
		List<User> usrs = userDao.listAll();
		List<UserDto> resp = new ArrayList<UserDto>();
		for (User u : usrs)
			resp.add(new UserDto(u.getId(), u.getUsername(), u.getPassword(), u.getRole().getRole()));
		return resp;
	}
	
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public void updateUser(@RequestBody UserDto userDto) {
		log.info("hi admin");
		User user = userDao.get(userDto.getId());
		if(user != null) {
			user.setUsername(userDto.getUsername());
			if(!userDto.getPassword().isEmpty())
				user.setPassword(passwordEncoder.encode(userDto.getPassword()));
			userDao.update(user);
		}
	}
}
