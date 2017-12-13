package com.tfkfan.vaadin.ui.widgets;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import com.vaadin.ui.Label;

public class UserLabel extends Label {
	private static final long serialVersionUID = 1L;
	private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-mm HH:mm:ss");

	public UserLabel(String username) {
		super("You are signed in as (" + username + "). " + formatter.format(LocalDateTime.now()));
	}
}
