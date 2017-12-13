package com.tfkfan.hibernate.entities;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;

@Entity
@Table(name = "users")
public class User{

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	protected int id;

	@Column(name = "username")
	protected String username;

	@Column(name = "password")
	protected String password;

	@ManyToOne
	@JoinColumn(name = "id_role")
	protected Role role;

	@OneToMany(mappedBy = "autor")
	protected Set<Theme> themes = new HashSet<Theme>();

	@OneToMany(mappedBy = "user")
	protected Set<Message> messages = new HashSet<Message>();

	public User() {

	}

	public User(String username, String password) {
		setUsername(username);
		setPassword(password);
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public Set<Theme> getThemes() {
		return themes;
	}

	public void setThemes(Set<Theme> themes) {
		this.themes = themes;
	}

	public Set<Message> getMessages() {
		return messages;
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

		User user = (User) obj;
		return id == user.getId();
	}
}
