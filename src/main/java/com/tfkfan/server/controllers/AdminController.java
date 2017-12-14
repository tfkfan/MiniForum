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

import com.tfkfan.hibernate.dao.RoleDao;
import com.tfkfan.hibernate.dao.UserAlreadyExistsException;
import com.tfkfan.hibernate.dao.UserDao;
import com.tfkfan.hibernate.entities.Role;
import com.tfkfan.hibernate.entities.User;
import com.tfkfan.security.enums.UserRole;
import com.tfkfan.server.service.dto.UserDto;

@RestController
@RequestMapping("/_admin")
public class AdminController {
	private final static Logger log = Logger.getLogger(AdminController.class.getName());
	@Autowired
	UserDao userDao;
	
	@Autowired
	RoleDao roleDao;
	
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
		User user = userDao.get(userDto.getId());
		if(user != null) {
			user.setUsername(userDto.getUsername());
			if(!userDto.getPassword().isEmpty())
				user.setPassword(passwordEncoder.encode(userDto.getPassword()));
			if(!userDto.getRole().isEmpty() ) {
				Role role = roleDao.getRoleByName(userDto.getRole());
				if(role != null)
					user.setRole(role);
			}
			userDao.update(user);
		}
	}
	
	@RequestMapping(value = "/add", method = RequestMethod.PUT)
	public void addUser(@RequestBody UserDto userDto) throws UserAlreadyExistsException{
		User user = userDao.findByUsername(userDto.getUsername());
		if(user != null) {
			log.info("already exists");
			throw new UserAlreadyExistsException("Invalid username");
		}else {
			user = new User();
			user.setUsername(userDto.getUsername());
			if(!userDto.getPassword().isEmpty())
				user.setPassword(passwordEncoder.encode(userDto.getPassword()));
			user.setRole(roleDao.getRoleByName(UserRole.ROLE_USER.getRole()));
			
			userDao.save(user);
		}
	}
}
