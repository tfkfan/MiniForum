package com.tfkfan.vaadin.ui.widgets;

import com.vaadin.event.ShortcutAction;
import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

public class AuthForm extends VerticalLayout {
	private static final long serialVersionUID = 1L;

	private TextField userName;

	private PasswordField passwordField;
	private CheckBox rememberMe;

	private Button login;
	private Button signup;

	private Label loginFailedLabel;
	private Label loggedOutLabel;

	public AuthForm(VaadinRequest request, Boolean isRememberMe, Boolean isLoginForm) {
		FormLayout loginForm = new FormLayout();
		loginForm.setSizeUndefined();

		userName = new TextField("Username");
		passwordField = new PasswordField("Password");

		if (isRememberMe)
			rememberMe = new CheckBox("Remember me");

		HorizontalLayout hl = new HorizontalLayout();
		login = new Button("Login");

		login.addStyleName(ValoTheme.BUTTON_PRIMARY);
		login.setDisableOnClick(true);
		login.setClickShortcut(ShortcutAction.KeyCode.ENTER);

		signup = new Button("Sign up");
		signup.addStyleName(ValoTheme.BUTTON_FRIENDLY);

		if(isLoginForm)
			hl.addComponents(login, signup);
		else
			hl.addComponents(signup, login);
		
		loginForm.addComponents(userName, passwordField);
		if (isRememberMe)
			loginForm.addComponent(rememberMe);
		loginForm.addComponent(hl);

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

		addComponent(loginLayout);
		setSizeFull();
		setComponentAlignment(loginLayout, Alignment.MIDDLE_CENTER);
	}

	public void updateWithError(String message) {
		userName.focus();
		userName.selectAll();
		passwordField.setValue("");
		loginFailedLabel.setValue(String.format("Login failed: %s", message));
		loginFailedLabel.setVisible(true);
		if (loggedOutLabel != null) {
			loggedOutLabel.setVisible(false);
		}
	}

	public String getUsername() {
		return userName.getValue();
	}

	public String getPassword() {
		return passwordField.getValue();
	}

	public Boolean getRememberMe() {
		return rememberMe.getValue();
	}

	public void setLoginClick(ClickListener func) {
		login.addClickListener(func);
	}

	public void setSignUpClick(ClickListener func) {
		signup.addClickListener(func);
	}
}
