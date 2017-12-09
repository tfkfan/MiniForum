package com.tfkfan.mvc.controllers;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import com.tfkfan.hibernate.dao.IDao;
import com.tfkfan.hibernate.entities.User;

@Controller
public class ApplicationController {

	@Autowired
	IDao<User> userDao;
	
	@RequestMapping(value= "/", method = RequestMethod.GET)
	public String home(){
		try {
			String pass = "mypass";
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.reset();
			md.update(pass.getBytes(),0,pass.length());
			User user = new User("user1", new BigInteger(1,md.digest()).toString(16));
			userDao.save(user);
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "home";
	}
}
