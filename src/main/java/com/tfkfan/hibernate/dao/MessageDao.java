package com.tfkfan.hibernate.dao;

import java.util.Arrays;
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

	public List<Message> getAllNotPublishedMessages() {
		return this.listAllOrderByWithParam("is_published='false'", "date", false);
	}

	public List<Message> getAllPublishedMessagesByThemeId(Long themeId) {
		return this.listAllOrderByWithParam(Arrays.asList("is_published='true'", "id_theme=" + themeId), "date", false);
	}
}
