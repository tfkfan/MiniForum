package com.tfkfan.server.service.impl;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.tfkfan.hibernate.dao.MessageDao;
import com.tfkfan.hibernate.entities.Message;
import com.tfkfan.server.service.dto.MessageDto;

@Service("messageService")
public class MessageService extends AbstractService<Message> {

	@Autowired
	ThemeService themeService;
	
	@Autowired
	UserService userService;
	
	private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-mm HH:mm:ss");
	
	public MessageService() {

	}

	public void addMessage(MessageDto message) throws Exception {
		if (message != null) {
			Message msg = new Message();
			msg.setDate(formatter.format(LocalDateTime.now()));
			msg.setIs_published(false);
			msg.setUser(userService.get(message.getUser_id()));
			msg.setTheme(themeService.get(message.getId_theme()));
			msg.setText(message.getText());
			add(msg);
		}
	}

	public List<Message> listNotPublished() {
		return ((MessageDao) getDao()).getAllNotPublishedMessages();
	}
	
	public List<Message> listPublished() {
		return ((MessageDao) getDao()).getAllPublishedMessages();
	}
	
	public void publish(Long id) throws Exception{
		Message msg = get(id);
		msg.setIs_published(true);
		update(msg);
	}
	
	public List<MessageDto> getAllPublishedMessagesDTO(){
		return convertToDto(listPublished());
	}
	
	public List<MessageDto> getAllNotPublishedMessagesDTO(){
		return convertToDto(listNotPublished());
	}
	
	public List<MessageDto> getAllThemeMessagesDTO(Long theme_id) throws Exception{
		return convertToDto(themeService.get(theme_id).getMessages());
	}
	
	private List<MessageDto> convertToDto(Collection<Message> msgs){
		List<MessageDto> resp = new ArrayList<MessageDto>();
		for (Message m : msgs)
			resp.add(new MessageDto(m.getId(), m.getTheme().getId(), m.getUser().getId(), m.getText(),
					m.getTheme().getTitle(), m.getDate(), m.getUser().getUsername(), m.getIs_published()));
		return resp;
	}
}
