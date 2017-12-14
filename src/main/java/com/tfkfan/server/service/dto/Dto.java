package com.tfkfan.server.service.dto;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

public abstract class Dto implements Serializable{
	
	protected Long id;

	private static final long serialVersionUID = 1L;

	public Dto(){
		
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
}
