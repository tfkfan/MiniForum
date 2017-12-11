package com.tfkfan.mvc.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.tfkfan.hibernate.dao.impl.UserDao;
import com.tfkfan.hibernate.entities.User;

@Controller
public class ApplicationController {

	@Autowired
	UserDao userDao;
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public ModelAndView home(Authentication authentication) {
		ModelAndView mv = new ModelAndView("home");
		User user = new User();
		user.setUsername(authentication.getName());
		user.setPassword("saasdg");
		userDao.save(user);
		String role = "";
		for (GrantedAuthority authority : authentication.getAuthorities())
			role += authority.getAuthority();

		mv.addObject("role", role);
		mv.addObject("username", authentication.getName());
		return mv;
	}

	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public ModelAndView login(@RequestParam(value = "error", required = false) String error,
			@RequestParam(value = "logout", required = false) String logout) {

		ModelAndView model = new ModelAndView();
		if (error != null)
			model.addObject("error", "Invalid username or password");

		if (logout != null)
			model.addObject("msg", "You've been logged out successfully.");

		model.setViewName("login");
		return model;

	}

	@RequestMapping(value = "/admin**", method = RequestMethod.GET)
	public ModelAndView adminPage() {

		ModelAndView model = new ModelAndView();
		model.addObject("title", "Spring Security + Hibernate Example");
		model.addObject("message", "This page is for ROLE_ADMIN only!");
		model.setViewName("admin");

		return model;

	}

}