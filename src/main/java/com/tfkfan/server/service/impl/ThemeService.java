package com.tfkfan.server.service.impl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.tfkfan.hibernate.entities.Theme;
import com.tfkfan.hibernate.entities.User;
import com.tfkfan.server.service.dto.ThemeDto;

@Service("themeService")
public class ThemeService extends AbstractService<Theme> {
	@Autowired
	UserService userService;
	
	public ThemeService() {

	}

	public void addTheme(String themeTitle, User user) throws Exception {
		Theme theme = new Theme(LocalDateTime.now().toString(), themeTitle, user);
		add(theme);
	}
	
	public void addTheme(ThemeDto themeDto) throws Exception{
		User user = userService.getUserByUsername(themeDto.getAutor());
		if (user != null) 
			addTheme(themeDto.getTitle(), user);
	}

	public List<ThemeDto> listAllDTO() throws Exception {
		return convertToDto(getAll());
	}

	public ThemeDto getDTO(Long id) throws Exception {
		Theme t = get(id);
		if (t != null)
			return new ThemeDto(id, t.getTitle(), t.getDate(), t.getAutor().getUsername());
		else
			return null;
	}

	public List<ThemeDto> convertToDto(Collection<Theme> tms) {
		List<ThemeDto> resp = new ArrayList<ThemeDto>();
		for (Theme t : tms)
			resp.add(new ThemeDto(t.getId(), t.getTitle(), t.getDate(), t.getAutor().getUsername()));

		return resp;
	}

}
