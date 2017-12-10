package com.tfkfan.controllers;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ApplicationController {

	@RequestMapping("/")
	public ModelAndView home(Authentication authentication) {
		ModelAndView mv = new ModelAndView("home");
		String role = "";
		for (GrantedAuthority authority : authentication.getAuthorities()) 
		     role += authority.getAuthority();
		 
		mv.addObject("role", role);
		mv.addObject("username", authentication.getName());
		return mv;
	}
}	