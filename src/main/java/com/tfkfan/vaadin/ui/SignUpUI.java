package com.tfkfan.vaadin.ui;

import com.tfkfan.hibernate.dao.UserAlreadyExistsException;
import com.tfkfan.server.service.impl.UserService;
import com.tfkfan.vaadin.ui.widgets.AuthForm;
import com.vaadin.annotations.Theme;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.*;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
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
	UserService userService;

	@Override
	protected void init(VaadinRequest request) {
		getPage().setTitle("Vaadin forum Sign Up");

		FormLayout loginForm = new FormLayout();
		loginForm.setSizeUndefined();
		form = new AuthForm(request, false, false);

		form.setSignUpClick(e -> signUp());
		form.setLoginClick(e -> getPage().setLocation(LOGIN_PAGE));

		setContent(form);
		setSizeFull();
	}

	private void signUp() {
		try {
			userService.createNewUser(form.getUsername(), form.getPassword());
			getPage().setLocation(LOGIN_PAGE);
		} catch (AuthenticationException | UserAlreadyExistsException ex) {
			form.updateWithError(ex.getMessage());
		} catch (Exception ex) {
			Notification.show("An unexpected error occurred", ex.getMessage(), Notification.Type.ERROR_MESSAGE);
			LoggerFactory.getLogger(getClass()).error("Unexpected error while logging in", ex);
		}
	}
}
