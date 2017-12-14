package com.tfkfan.server.controllers;

import java.util.List;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.tfkfan.server.service.dto.UserDto;
import com.tfkfan.server.service.impl.UserService;

@RestController
@RequestMapping("/_admin")
public class AdminController {
	private final static Logger log = Logger.getLogger(AdminController.class.getName());

	@Autowired
	UserService userService;

	//private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-mm HH:mm:ss");

	@RequestMapping(method = RequestMethod.GET)
	public List<UserDto> users() throws Exception {
		return userService.listAllUserDTO();
	}
	
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public void updateUser(@RequestBody UserDto userDto) throws Exception {
		userService.updateUser(userDto);
	}
	
	@RequestMapping(value = "/add", method = RequestMethod.PUT)
	public void addUser(@RequestBody UserDto userDto) throws Exception{
		userService.createNewUser(userDto);
	}
}
