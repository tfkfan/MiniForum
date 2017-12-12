package com.tfkfan.vaadin.ui.view;

import com.tfkfan.hibernate.dao.UserDao;
import com.tfkfan.hibernate.entities.User;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;

import javax.annotation.PostConstruct;
import java.util.List;

@Secured({"ROLE_ADMIN"})
@SpringView(name = AdminUsersView.VIEW_NAME)
public class AdminUsersView extends VerticalLayout implements View {
	private static final long serialVersionUID = 1L;

	public static final String VIEW_NAME = "admin";

    @Autowired
    UserDao userRepository;

    @PostConstruct
    public void init() {
        addComponent(new Label("Hello, this is admin view."));

        List<User> users = userRepository.listAll();

        Grid<User> grid = new Grid<>();
        grid.setSizeFull();
        grid.setItems(users);
        grid.addColumn(User::getUsername).setCaption("Name");
        grid.addColumn(User::getPassword).setCaption("Password");

        addComponent(grid);
        setExpandRatio(grid, 1f);
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {

    }
}

