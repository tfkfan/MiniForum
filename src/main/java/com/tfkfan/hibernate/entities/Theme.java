package com.tfkfan.hibernate.entities;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;

@Entity
@Table(name = "themes")
public class Theme {
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "date")
	private String date;

	@Column(name = "title")
	private String title;

	@ManyToOne
	@JoinColumn(name = "id_autor")
	protected User autor;

	@OneToMany (fetch = FetchType.EAGER, mappedBy = "theme", cascade = CascadeType.ALL)
	protected Set<Message> messages = new HashSet<Message>();

	public Theme() {

	}

	public Theme(String date, String title, User autor) {
		setDate(date);
		setTitle(title);
		setAutor(autor);
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public User getAutor() {
		return autor;
	}

	public void setAutor(User autor) {
		this.autor = autor;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	public Set<Message> getMessages() {
		return messages;
	}
	
	public Set<Message> getPublishedMessages(){
		Set<Message> msgs = new HashSet<Message>();
		for(Message msg : messages)
			if(msg.getIs_published())
				msgs.add(msg);
		
		return msgs;
	}

	public void setMessages(Set<Message> messages) {
		this.messages = messages;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == this)
			return true;
		if (obj == null || obj.getClass() != this.getClass())
			return false;

		Theme theme = (Theme)obj;
		return id == theme.getId();
	}
}
