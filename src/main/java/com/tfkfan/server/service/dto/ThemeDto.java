package com.tfkfan.server.service.dto;

import java.io.Serializable;

public class ThemeDto extends Dto{
	protected String title;
	protected String date;
	protected String autor;
	
	private static final long serialVersionUID = 1L;
	
	public ThemeDto(){
		
	}
	
	public ThemeDto(Long id, String title, String date, String autor){
		setId(id);
		setTitle(title);
		setDate(date);
		setAutor(autor);
	}
	
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getAutor() {
		return autor;
	}

	public void setAutor(String autor) {
		this.autor = autor;
	}
}
