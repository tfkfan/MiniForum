package com.tfkfan.vaadin.ui.view;

import com.tfkfan.hibernate.dao.UserDao;
import com.tfkfan.hibernate.entities.User;
import com.vaadin.data.Binder;
import com.vaadin.data.Binder.Binding;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Grid.Column;
import com.vaadin.ui.Grid.GridContextClickEvent;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;

import javax.annotation.PostConstruct;
import java.util.List;

@Secured({ "ROLE_ADMIN" })
@SpringView(name = AdminUsersView.VIEW_NAME)
public class AdminUsersView extends VerticalLayout implements View {
	private static final long serialVersionUID = 1L;

	public static final String VIEW_NAME = "admin";

	@Autowired
	UserDao userDao;

	@PostConstruct
	public void init() {
		addComponent(new Label("This is admin module view"));
		
		Grid<User> grid = createUsersGrid();
		
		addComponent(grid);
		setExpandRatio(grid, 1f);
	}

	@Override
	public void enter(ViewChangeListener.ViewChangeEvent event) {

	}

	protected Grid<User> createUsersGrid() {
		List<User> users = userDao.listAll();
		Grid<User> grid = new Grid<>();
		
		grid.setSizeFull();
		grid.setItems(users);
		
		TextField nameField = new TextField();


		grid.addColumn(User::getUsername).setEditorComponent(
		    nameField, (user, username) -> {
		    	user.setUsername(username);
		    	userDao.update(user);
		    }).setCaption("User Name").setExpandRatio(1);

		grid.getEditor().setEnabled(true);
		grid.addColumn(User::getPassword).setCaption("Password");
		
		
		return grid;
	}
}
