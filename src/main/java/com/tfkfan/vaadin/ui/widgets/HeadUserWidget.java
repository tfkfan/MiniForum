package com.tfkfan.vaadin.ui.widgets;

import org.vaadin.spring.security.VaadinSecurity;

import com.tfkfan.hibernate.entities.User;
import com.tfkfan.security.enums.UserRole;
import com.vaadin.server.ExternalResource;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Link;

public class HeadUserWidget extends HorizontalLayout {
	private static final long serialVersionUID = 1L;

	User currentUser;
	VaadinSecurity vaadinSecurity;

	public HeadUserWidget(User currentUser, VaadinSecurity vaadinSecurity) {
		super();
		this.currentUser = currentUser;
		this.vaadinSecurity = vaadinSecurity;
	}

	public void customInit(Component... widgets) {
		addComponents(new UserLabel(currentUser.getUsername()), new Button("Logout", e -> vaadinSecurity.logout()));
		
		if (widgets != null)
			addComponents(widgets);

		addComponents(new Link("Home", new ExternalResource("/")));

		String userRole = currentUser.getRole().getRole();
		if (userRole.equals(UserRole.ROLE_MODERATOR.getRole()) || userRole.equals(UserRole.ROLE_ADMIN.getRole()))
			addComponents(new Link("Moderator page", new ExternalResource("/moderate")));

		if (userRole.equals(UserRole.ROLE_ADMIN.getRole()))
			addComponent(new Link("Admin page", new ExternalResource("/admin")));
	}

}
