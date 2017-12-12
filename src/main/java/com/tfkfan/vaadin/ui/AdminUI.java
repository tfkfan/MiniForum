package com.tfkfan.vaadin.ui;

import com.tfkfan.hibernate.dao.UserDao;
import com.tfkfan.hibernate.entities.User;
import com.tfkfan.security.SecurityContextUtils;
import com.tfkfan.vaadin.ui.view.AccessDeniedView;
import com.tfkfan.vaadin.ui.view.AdminUsersView;
import com.tfkfan.vaadin.ui.view.ErrorView;
import com.tfkfan.vaadin.ui.view.UserView;
import com.vaadin.annotations.Theme;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.navigator.ViewDisplay;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.spring.annotation.SpringViewDisplay;
import com.vaadin.spring.navigator.SpringNavigator;
import com.vaadin.spring.navigator.SpringViewProvider;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.security.access.annotation.Secured;
import org.vaadin.spring.security.VaadinSecurity;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.util.List;


@Theme("Demo")
@Secured({"ROLE_ADMIN"})
@SpringUI(path = "/admin")
@SpringViewDisplay
public class AdminUI extends UI implements ViewDisplay {
	private static final long serialVersionUID = 1L;

	@Autowired
	ApplicationContext applicationContext;

	@Autowired
	VaadinSecurity vaadinSecurity;

	@Autowired
	SpringViewProvider springViewProvider;

	@Autowired
	SpringNavigator springNavigator;

	Panel springViewDisplay;

	@PostConstruct
	public void init() {
		springNavigator.setErrorView(ErrorView.class);
		springViewProvider.setAccessDeniedViewClass(AccessDeniedView.class);
		springViewDisplay = new Panel();
		springViewDisplay.setSizeFull();
	}

	@Override
	protected void init(VaadinRequest request){
		getPage().setTitle("Vaadin Security Demo");

		final CssLayout navigationBar = new CssLayout();
		navigationBar.addStyleName(ValoTheme.LAYOUT_COMPONENT_GROUP);
		navigationBar.addComponent(createNavigationButton("Users", AdminUsersView.VIEW_NAME));
		navigationBar.addComponent(new Button("Logout", e -> vaadinSecurity.logout()));

		final VerticalLayout root = new VerticalLayout();
		root.setSizeFull();
		root.addComponents(new Label(SecurityContextUtils.getUser().getUsername() + " : " + LocalDateTime.now()));
		root.addComponent(navigationBar);
		root.addComponent(springViewDisplay);
		root.setExpandRatio(springViewDisplay, 1.0f);

		setContent(root);
	}

	@Override
	public void showView(View view) {
		springViewDisplay.setContent((Component)view);
	}

	private Button createNavigationButton(String caption, final String viewName) {
		Button button = new Button(caption);
		button.addClickListener(event -> getUI().getNavigator().navigateTo(viewName));
		return button;
	}

}

