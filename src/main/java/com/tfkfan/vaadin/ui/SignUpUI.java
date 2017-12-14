package com.tfkfan.vaadin.ui;

import com.tfkfan.hibernate.dao.RoleDao;
import com.tfkfan.hibernate.dao.UserAlreadyExistsException;
import com.tfkfan.hibernate.dao.UserDao;
import com.tfkfan.hibernate.entities.Role;
import com.tfkfan.hibernate.entities.User;
import com.tfkfan.security.enums.UserRole;
import com.tfkfan.vaadin.ui.widgets.AuthForm;
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

	private AuthForm form;

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
		form = new AuthForm(request, false, false);

		form.setSignUpClick(e -> signUp());
		form.setLoginClick(e -> getPage().setLocation(LOGIN_PAGE));

		userRole = roleDao.getRoleByName(UserRole.ROLE_USER.getRole());

		setContent(form);
		setSizeFull();
	}

	private void signUp() {
		try {
			User user = new User();
			String username = form.getUsername();
			String password = pe.encode(form.getPassword());

			user.setUsername(username);
			user.setPassword(password);
			user.setRole(userRole);
			userDao.createUser(user);
			vaadinSecurity.login(username, form.getPassword());
		} catch (AuthenticationException | UserAlreadyExistsException ex) {
			form.updateWithError(ex.getMessage());
		} catch (Exception ex) {
			Notification.show("An unexpected error occurred", ex.getMessage(), Notification.Type.ERROR_MESSAGE);
			LoggerFactory.getLogger(getClass()).error("Unexpected error while logging in", ex);
		}
	}
}
