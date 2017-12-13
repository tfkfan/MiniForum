package com.tfkfan.vaadin.ui;

import com.tfkfan.hibernate.dao.RoleDao;
import com.tfkfan.hibernate.dao.UserAlreadyExistsException;
import com.tfkfan.hibernate.dao.UserDao;
import com.tfkfan.hibernate.entities.Role;
import com.tfkfan.hibernate.entities.User;
import com.tfkfan.security.enums.UserRole;
import com.vaadin.annotations.Theme;
import com.vaadin.event.ShortcutAction;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.vaadin.spring.security.shared.VaadinSharedSecurity;
import static com.tfkfan.server.ServerUtils.SIGNUP_PAGE;
import static com.tfkfan.server.ServerUtils.LOGIN_PAGE;

@SpringUI(path = SIGNUP_PAGE)
@Theme("Demo")
public class SignUpUI extends UI {
	private static final long serialVersionUID = 1L;

	@Autowired
	VaadinSharedSecurity vaadinSecurity;

	private TextField userName;

	private PasswordField passwordField;

	private Button signup;
	private Button login;

	private Label loginFailedLabel;
	private Label loggedOutLabel;

	@Autowired
	UserDao userDao;
	
	@Autowired
	PasswordEncoder pe;

	@Autowired
	RoleDao roleDao;

	Role userRole;

	@Override
	protected void init(VaadinRequest request) {
		getPage().setTitle("Vaadin forum Sign Up");

		FormLayout loginForm = new FormLayout();
		loginForm.setSizeUndefined();

		userRole = roleDao.getRoleByName(UserRole.ROLE_USER.getRole());

		userName = new TextField("Username");
		passwordField = new PasswordField("Password");
		
		HorizontalLayout hl = new HorizontalLayout();
		signup = new Button("Sign up");
		signup.addStyleName(ValoTheme.BUTTON_FRIENDLY);
		signup.setDisableOnClick(true);
		signup.setClickShortcut(ShortcutAction.KeyCode.ENTER);
		signup.addClickListener(e -> signUp());
		
		
		login = new Button("Log in");
		login.addStyleName(ValoTheme.BUTTON_PRIMARY);
		login.addClickListener(e -> getPage().setLocation(LOGIN_PAGE));
		
		hl.addComponents(signup, login);

		loginForm.addComponents(userName, passwordField, hl);

		VerticalLayout loginLayout = new VerticalLayout();
		loginLayout.setSpacing(true);
		loginLayout.setSizeUndefined();

		if (request.getParameter("logout") != null) {
			loggedOutLabel = new Label("You have been logged out!");
			loggedOutLabel.addStyleName(ValoTheme.LABEL_SUCCESS);
			loggedOutLabel.setSizeUndefined();
			loginLayout.addComponent(loggedOutLabel);
			loginLayout.setComponentAlignment(loggedOutLabel, Alignment.BOTTOM_CENTER);
		}

		loginLayout.addComponent(loginFailedLabel = new Label());
		loginLayout.setComponentAlignment(loginFailedLabel, Alignment.BOTTOM_CENTER);
		loginFailedLabel.setSizeUndefined();
		loginFailedLabel.addStyleName(ValoTheme.LABEL_FAILURE);
		loginFailedLabel.setVisible(false);

		loginLayout.addComponent(loginForm);
		loginLayout.setComponentAlignment(loginForm, Alignment.TOP_CENTER);

		VerticalLayout rootLayout = new VerticalLayout(loginLayout);
		rootLayout.setSizeFull();
		rootLayout.setComponentAlignment(loginLayout, Alignment.MIDDLE_CENTER);
		setContent(rootLayout);
		setSizeFull();
	}

	private void signUp() {
		try {
			User user = new User();
			String username = userName.getValue();
			String password = pe.encode(passwordField.getValue());

			user.setUsername(username);
			user.setPassword(password);
			user.setRole(userRole);
			userDao.createUser(user);
			vaadinSecurity.login(username, passwordField.getValue());
		} catch (AuthenticationException | UserAlreadyExistsException ex) {
			userName.focus();
			userName.selectAll();
			passwordField.setValue("");
			loginFailedLabel.setValue(String.format("Login failed: %s", ex.getMessage()));
			loginFailedLabel.setVisible(true);
			if (loggedOutLabel != null) {
				loggedOutLabel.setVisible(false);
			}
		} catch (Exception ex) {
			Notification.show("An unexpected error occurred", ex.getMessage(), Notification.Type.ERROR_MESSAGE);
			LoggerFactory.getLogger(getClass()).error("Unexpected error while logging in", ex);
		} finally {
			signup.setEnabled(true);
		}
	}
}
