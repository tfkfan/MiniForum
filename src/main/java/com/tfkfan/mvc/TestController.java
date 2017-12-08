package com.tfkfan.mvc;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.tfkfan.mvc.hibernate.dao.impl.PersonDao;

@Controller
public class TestController {
	
	@RequestMapping(value= "/test", method = RequestMethod.GET)
	public String home(){
		//PersonDao dao = new PersonDao();
		//dao.savePerson();
		return "home";
	}
}
