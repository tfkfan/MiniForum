package com.tfkfan.vaadin.ui.widgets;

import org.vaadin.spring.security.VaadinSecurity;

import com.tfkfan.hibernate.entities.User;
import com.tfkfan.security.enums.UserRole;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.MenuBar;
import static com.tfkfan.server.ServerUtils.MODER_PAGE;
import static com.tfkfan.server.ServerUtils.ADMIN_PAGE;
import static com.tfkfan.server.ServerUtils.HOME_PAGE;

public class HeaderBarWidget extends HorizontalLayout {
	private static final long serialVersionUID = 1L;

	User currentUser;
	VaadinSecurity vaadinSecurity;

	public HeaderBarWidget(User currentUser, VaadinSecurity vaadinSecurity) {
		super();
		this.currentUser = currentUser;
		this.vaadinSecurity = vaadinSecurity;
	}

	public void customInit(Component... widgets) {
		MenuBar menu = new MenuBar();
		menu.addItem("Home", null, item -> getUI().getPage().setLocation(HOME_PAGE));

		String userRole = currentUser.getRole().getRole();
		if (userRole.equals(UserRole.ROLE_MODERATOR.getRole()) || userRole.equals(UserRole.ROLE_ADMIN.getRole()))
			menu.addItem("Moderator page", null, item -> getUI().getPage().setLocation(MODER_PAGE));

		if (userRole.equals(UserRole.ROLE_ADMIN.getRole()))
			menu.addItem("Admin page", null, item -> getUI().getPage().setLocation(ADMIN_PAGE));

		menu.addItem("Logout", null, item -> vaadinSecurity.logout());

		addComponents(menu, new UserLabel(currentUser.getUsername()));

		if (widgets != null)
			addComponents(widgets);

	}

}
