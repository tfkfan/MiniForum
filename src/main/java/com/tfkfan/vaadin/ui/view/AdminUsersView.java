package com.tfkfan.vaadin.ui.view;

import com.tfkfan.security.enums.UserRole;
import com.tfkfan.server.RequestMaker;
import com.tfkfan.server.service.dto.UserDto;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.Button;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.Grid;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.NativeSelect;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.renderers.ButtonRenderer;
import com.vaadin.ui.renderers.TextRenderer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.vaadin.spring.security.VaadinSecurity;
import java.util.List;
import javax.annotation.PostConstruct;

@Secured({ "ROLE_ADMIN" })
@SpringView(name = AdminUsersView.VIEW_NAME)
public class AdminUsersView extends VerticalLayout implements View {
	private static final long serialVersionUID = 1L;

	public static final String VIEW_NAME = "admin";


	private RequestMaker<UserDto> rm;


	@Autowired
	PasswordEncoder passwordEncoder;

	@Autowired
	VaadinSecurity vaadinSecurity;

	@PostConstruct
	public void init() {
		rm = new RequestMaker<UserDto>(UserDto.class);

		addComponent(new Label("This is admin module view"));

		Grid<UserDto> grid = createUsersGrid();

		addComponent(grid);
		setExpandRatio(grid, 1f);
	}

	@Override
	public void enter(ViewChangeListener.ViewChangeEvent event) {

	}

	protected Grid<UserDto> createUsersGrid() {
		Grid<UserDto> grid = new Grid<UserDto>();
		grid.setSizeFull();

		grid.setItems(rm.get("/_admin", new ParameterizedTypeReference<List<UserDto>>() {
		}));
		grid.addColumn(UserDto::getUsername).setCaption("User Name");
		grid.addColumn(user -> user.getRole(), new TextRenderer()).setCaption("Role");
		grid.addColumn(user -> "Edit", new ButtonRenderer<UserDto>(clickEvent -> edit(clickEvent.getItem())))
				.setCaption("Edit");
		return grid;
	}

	protected void edit(UserDto user) {
		Window subWindow = new Window("Sub-window");
		subWindow.setWidth("30%");
		VerticalLayout subContent = new VerticalLayout();
		subWindow.setContent(subContent);

		FormLayout form = new FormLayout();

		TextField usernameField = new TextField("Username");
		usernameField.setValue(user.getUsername());
		form.addComponent(usernameField);

		TextField passwordField = new TextField("Password");

		form.addComponent(passwordField);

		NativeSelect<String> select = new NativeSelect<>("Role");

		select.clear();
		select.setItems(UserRole.ROLE_ADMIN.getRole(), UserRole.ROLE_USER.getRole(), UserRole.ROLE_MODERATOR.getRole());
		select.setSelectedItem(user.getRole());

		form.addComponent(select);
		HorizontalLayout btns = new HorizontalLayout();

		Button formBtn = new Button("Save");

		formBtn.addClickListener(event -> {
			saveClick(user, usernameField.getValue(), passwordField.getValue(), select.getSelectedItem().get());
			subWindow.close();
			getUI().getPage().reload();
		});

		Button formCloseBtn = new Button("Close");
		formCloseBtn.addClickListener(event -> subWindow.close());
		btns.addComponents(formBtn, formCloseBtn);

		form.addComponent(btns);

		subContent.addComponent(form);
		subWindow.center();

		getUI().addWindow(subWindow);
	}

	protected void saveClick(UserDto user, String username, String password, String role_selected) {
		if (!password.isEmpty())
			password = passwordEncoder.encode(password);
		else
			password = "";

		user.setUsername(username);
		user.setPassword(password);
		user.setRole(role_selected);
		rm.post(user, "/_admin/update");
	}
}
