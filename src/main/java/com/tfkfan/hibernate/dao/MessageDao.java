package com.tfkfan.hibernate.dao;

import java.util.List;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import com.tfkfan.hibernate.entities.Message;

@Component("messageDao")
@Scope("singleton")
@Transactional
public class MessageDao extends AbstractDao<Message> {

	public MessageDao() {
		super(Message.class);

	}

	@SuppressWarnings("unchecked")
	public List<Message> getAllNotPublishedMessages() {
		return (List<Message>) getTemplate().find("FROM Message WHERE is_published='false'");
	}
}
