package com.tfkfan.server.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.tfkfan.server.service.dto.MessageDto;
import com.tfkfan.server.service.impl.MessageService;
import java.util.List;
import java.util.logging.Logger;

@RestController
@RequestMapping("/messages")
public class MessagesController {
	private final static Logger log = Logger.getLogger(MessagesController.class.getName());

	@Autowired
	MessageService msgService;

	@RequestMapping(method = RequestMethod.GET)
	public List<MessageDto> messages() {
		return msgService.getAllPublishedMessagesDTO();
	}
	
	@RequestMapping(value = "/{id_theme}", method = RequestMethod.GET)
	public List<MessageDto> theme_messages(@PathVariable Long id_theme) throws Exception {
		return msgService.getAllThemeMessagesDTO(id_theme);
	}

	@RequestMapping(value = "/not_published", method = RequestMethod.GET)
	public List<MessageDto> not_published_messages() {
		return msgService.getAllNotPublishedMessagesDTO();
	}

	@RequestMapping(value = "/publish/{id}", method = RequestMethod.POST)
	public void publishMessage(@PathVariable Long id) throws Exception {
		msgService.publish(id);
	}

	@RequestMapping(value = "/add", method = RequestMethod.PUT)
	public void addhMessage(@RequestBody MessageDto message) throws Exception {
		msgService.addMessage(message);
	}

}
