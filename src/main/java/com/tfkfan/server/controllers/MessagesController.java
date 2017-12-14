package com.tfkfan.server.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.tfkfan.hibernate.dao.MessageDao;
import com.tfkfan.hibernate.dao.ThemeDao;
import com.tfkfan.hibernate.dao.UserDao;
import com.tfkfan.hibernate.entities.Message;
import com.tfkfan.server.service.dto.MessageDto;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

@RestController
@RequestMapping("/messages")
public class MessagesController {
	private final static Logger log = Logger.getLogger(MessagesController.class.getName());

	@Autowired
	UserDao userDao;

	@Autowired
	ThemeDao themeDao;

	@Autowired
	MessageDao msgDao;

	private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-mm HH:mm:ss");

	@RequestMapping(method = RequestMethod.GET)
	public List<MessageDto> messages() {
		List<Message> msgs = msgDao.listAll();
		List<MessageDto> resp = new ArrayList<MessageDto>();
		for (Message m : msgs)
			resp.add(new MessageDto(m.getId(), m.getTheme().getId(), m.getUser().getId(), m.getText(),
					m.getTheme().getTitle(), m.getDate(), m.getUser().getUsername(), m.getIs_published()));
		return resp;
	}

	@RequestMapping(value = "/not_published", method = RequestMethod.GET)
	public List<MessageDto> not_published_messages() {
		List<Message> msgs = msgDao.getAllNotPublishedMessages();
		List<MessageDto> resp = new ArrayList<MessageDto>();
		for (Message m : msgs)
			resp.add(new MessageDto(m.getId(), m.getTheme().getId(), m.getUser().getId(), m.getText(),
					m.getTheme().getTitle(), m.getDate(), m.getUser().getUsername(), m.getIs_published()));
		return resp;
	}

	@RequestMapping(value = "/publish/{id}", method = RequestMethod.PATCH)
	public void publishMessage(@PathVariable Long id) {
		Message msg = msgDao.get(id);
		if (msg != null) {
			msg.setIs_published(true);
			msgDao.update(msg);
		}

	}

	@RequestMapping(value = "/add", method = RequestMethod.PUT)
	public void addhMessage(@RequestBody MessageDto message) {
		if (message != null) {
			Message msg = new Message();
			msg.setDate(formatter.format(LocalDateTime.now()));
			msg.setIs_published(false);
			msg.setUser(userDao.get(message.getUser_id()));
			msg.setTheme(themeDao.get(message.getId_theme()));
			msg.setText(message.getText());
			msgDao.save(msg);
		}
	}

}
