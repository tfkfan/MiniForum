package com.tfkfan.mvc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.tfkfan.mvc.hibernate.dao.IPersonDao;
import com.tfkfan.mvc.hibernate.dao.impl.PersonDao;

@Controller
public class TestController {
	
	@Autowired
	IPersonDao dao;
	
	@RequestMapping(value= "/test", method = RequestMethod.GET)
	public String home(){
		System.out.println("home");
		dao.savePerson();
		return "home";
	}
}
