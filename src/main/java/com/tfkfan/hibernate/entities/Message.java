package com.tfkfan.hibernate.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "messages")
public class Message {

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	protected Long id;

	@Column(name = "text")
	protected String text;

	@Column(name = "date")
	protected String date;

	@Column(name = "is_published")
	protected String is_published;

	@ManyToOne
	@JoinColumn(name = "id_theme")
	protected Theme theme;

	@ManyToOne
	@JoinColumn(name = "id_user")
	protected User user;

	public Message() {

	}

	public Message(String text, String date, Theme theme, User user) {
		setText(text);
		setDate(date);
		setTheme(theme);
		setUser(user);
		setIs_published(false);
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public Boolean getIs_published() {
		return Boolean.valueOf(is_published);
	}

	public void setIs_published(Boolean is_published) {
		this.is_published = is_published.toString();
	}

	public Theme getTheme() {
		return theme;
	}

	public void setTheme(Theme theme) {
		this.theme = theme;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == this)
			return true;
		if (obj == null || obj.getClass() != this.getClass())
			return false;

		Message msg = (Message) obj;
		return id == msg.id;

	}

}