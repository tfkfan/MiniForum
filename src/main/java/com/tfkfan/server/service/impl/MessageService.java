package com.tfkfan.server.service.impl;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
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
		Message message = new Message(text, Date.from(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant()),
				theme, user);
		add(message);
	}

	public List<Message> listNotPublished() {
		return ((MessageDao) getDao()).getAllNotPublishedMessages();
	}

	public void publish(Message msg) throws Exception {
		msg.setIs_published(true);
		update(msg);
	}

	public List<Message> getSortedMessages() throws Exception {
		List<Message> tms = this.getAll();

		tms.sort((o1, o2) -> o1.getDate().compareTo(o2.getDate()));
		return tms;
	}

	public List<Message> getSortedMessagesById(Long themeId) throws Exception {
		return ((MessageDao) getDao()).getAllPublishedMessagesByThemeId(themeId);
	}

}
