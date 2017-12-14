package com.tfkfan.vaadin.ui;

import com.tfkfan.hibernate.dao.RoleDao;
import com.tfkfan.hibernate.dao.UserAlreadyExistsException;
import com.tfkfan.hibernate.dao.UserDao;
import com.tfkfan.hibernate.entities.Role;
import com.tfkfan.hibernate.entities.User;
import com.tfkfan.security.enums.UserRole;
import com.tfkfan.server.RequestMaker;
import com.tfkfan.server.service.dto.UserDto;
import com.tfkfan.vaadin.ui.widgets.AuthForm;
import com.vaadin.annotations.Theme;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.*;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.vaadin.spring.security.shared.VaadinSharedSecurity;
import static com.tfkfan.server.ServerUtils.SIGNUP_PAGE;
import static com.tfkfan.server.ServerUtils.LOGIN_PAGE;

@SpringUI(path = SIGNUP_PAGE)
@Theme("Demo")
public class SignUpUI extends UI {
	private static final long serialVersionUID = 1L;

	private AuthForm form;
	private RequestMaker<UserDto> rm;

	@Override
	protected void init(VaadinRequest request) {
		getPage().setTitle("Vaadin forum Sign Up");

		rm = new RequestMaker<UserDto>(UserDto.class);
		form = new AuthForm(request, false, false);
		//userRole = roleDao.getRoleByName(UserRole.ROLE_USER.getRole());

		form.setSignUpClick(e -> signUp());
		form.setLoginClick(e -> getPage().setLocation(LOGIN_PAGE));

		setContent(form);
		setSizeFull();
	}

	private void signUp() {
		try {
			UserDto user = new UserDto(form.getUsername(), form.getPassword());
			
			rm.put(new HttpEntity<UserDto>(user), "/_admin/add");

			getPage().setLocation(LOGIN_PAGE);
		} catch (AuthenticationException ex) {
			form.updateWithError(ex.getMessage());
		} catch (Exception ex) {
			Notification.show("An unexpected error occurred", ex.getMessage(), Notification.Type.ERROR_MESSAGE);
			LoggerFactory.getLogger(getClass()).error("Unexpected error while logging in", ex);
		} finally {
			// signup.setEnabled(true);
		}
	}
}
