package com.tfkfan.vaadin.ui;

import com.tfkfan.vaadin.ui.widgets.AuthForm;
import com.vaadin.annotations.Theme;
import com.vaadin.event.ShortcutAction;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.Notification;
import com.vaadin.ui.UI;
import com.vaadin.ui.themes.ValoTheme;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.vaadin.spring.security.shared.VaadinSharedSecurity;
import static com.tfkfan.server.ServerUtils.LOGIN_PAGE;
import static com.tfkfan.server.ServerUtils.SIGNUP_PAGE;

@SpringUI(path = LOGIN_PAGE)
@Theme("Demo")
public class LoginUI extends UI {
	private static final long serialVersionUID = 1L;

	@Autowired
	VaadinSharedSecurity vaadinSecurity;

	AuthForm form;

	@Override
	protected void init(VaadinRequest request) {
		getPage().setTitle("Vaadin forum");

		form = new AuthForm(request, true, true);
		form.setLoginClick(e -> login());
		form.setSignUpClick(e -> getPage().setLocation(SIGNUP_PAGE));
		setContent(form);
		setSizeFull();
	}

	private void login() {
		try {
			vaadinSecurity.login(form.getUsername(), form.getPassword(), form.getRememberMe());
		} catch (AuthenticationException ex) {
			form.updateWithError(ex.getMessage());
		} catch (Exception ex) {
			Notification.show("An unexpected error occurred", ex.getMessage(), Notification.Type.ERROR_MESSAGE);
			LoggerFactory.getLogger(getClass()).error("Unexpected error while logging in", ex);
		} finally {
			// login.setEnabled(true);
		}
	}
}
