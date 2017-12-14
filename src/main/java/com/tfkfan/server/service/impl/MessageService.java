package com.tfkfan.server.service.impl;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.tfkfan.hibernate.dao.MessageDao;
import com.tfkfan.hibernate.entities.Message;
import com.tfkfan.hibernate.entities.Theme;
import com.tfkfan.hibernate.entities.User;

@Service("messageService")
public class MessageService extends AbstractService<Message> {

	public MessageService() {

	}

	public void addMessage(String text, Theme theme, User user) throws Exception {
		Message message = new Message(text, LocalDateTime.now().toString(), theme, user);
		add(message);
	}

	public List<Message> listNotPublished() {
		return ((MessageDao) getDao()).getAllNotPublishedMessages();
	}
	
	public void publish(Message msg) throws Exception{
		msg.setIs_published(true);
		update(msg);
	}
}
