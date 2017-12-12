package com.tfkfan.hibernate.dao;

import java.util.List;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.tfkfan.hibernate.entities.Message;
import com.tfkfan.hibernate.entities.Theme;
import com.tfkfan.hibernate.entities.User;

@Component("messageDao")
@Scope("singleton")
@Transactional
public class MessageDao extends AbstractDao<Message> {
	
	public MessageDao() {
		super(Message.class);

	}
	
	public List<Message> getAllNotPublishedMessages(){
		return (List<Message>) getTemplate().find("FROM Message WHERE is_published=false");
	}
}
