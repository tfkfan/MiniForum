package com.tfkfan.server.service.impl;

import java.time.LocalDateTime;
import org.springframework.stereotype.Service;

import com.tfkfan.hibernate.entities.Message;
import com.tfkfan.hibernate.entities.Theme;
import com.tfkfan.hibernate.entities.User;

@Service("messageService")
public class MessageService extends AbstractService<Message> {

	public MessageService(){
		
	}
	
	public void addMessage(String text, Theme theme, User user) throws Exception{
		Message message = new Message(text, LocalDateTime.now().toString(), theme, user);
		add(message);
	}
}
