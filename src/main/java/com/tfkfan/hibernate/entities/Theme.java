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
	private User autor;

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
}
