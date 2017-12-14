package com.tfkfan.server.service.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class MessageDto extends Dto {
	protected String text;
	protected Long id_theme;
	protected String theme_title;

	protected Boolean is_published;
	protected String date;
	protected String user;
	protected Long user_id;

	private static final long serialVersionUID = 1L;

	public MessageDto() {

	}

	public MessageDto(Long id, Long id_theme, Long user_id, String text, String theme_title, String date, String user,
			Boolean is_published) {
		setId(id);
		setText(text);
		setDate(date);
		setId_theme(id_theme);
		setUser_id(user_id);
		setUser(user);
		setIs_published(is_published);
		setTheme_title(theme_title);
	}
	
	public MessageDto(Long id_theme, Long user_id, String text) {
		setText(text);
		setId_theme(id_theme);
		setUser_id(user_id);
	}

	public String getTheme_title() {
		return theme_title;
	}

	public void setTheme_title(String theme_title) {
		this.theme_title = theme_title;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public Long getId_theme() {
		return id_theme;
	}

	public void setId_theme(Long id_theme) {
		this.id_theme = id_theme;
	}

	public Boolean getIs_published() {
		return is_published;
	}

	public void setIs_published(Boolean is_published) {
		this.is_published = is_published;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public Long getUser_id() {
		return user_id;
	}

	public void setUser_id(Long user_id) {
		this.user_id = user_id;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	@Override
	public String toString() {
		return String.format(
				"MessageDto{id=%l, text=%s, user_id=%l, user=%s, is_published=%b, date=%s, id_theme=%l, theme_title=%s}",
				getId(), getText(), getUser_id(), getUser(), getIs_published(), getDate(), getId_theme(),
				getTheme_title());
	}
}
