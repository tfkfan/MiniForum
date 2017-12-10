package com.tfkfan.mvc.controllers;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ApplicationController {

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public ModelAndView home(Authentication authentication) {
		ModelAndView mv = new ModelAndView("home");
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

}