package com.tfkfan.vaadin.ui;

import com.tfkfan.hibernate.entities.User;
import com.tfkfan.security.SecurityContextUtils;
import com.tfkfan.vaadin.ui.view.AccessDeniedView;
import com.tfkfan.vaadin.ui.view.AdminUsersView;
import com.tfkfan.vaadin.ui.view.ErrorView;
import com.tfkfan.vaadin.ui.widgets.HeaderBarWidget;
import com.vaadin.annotations.Theme;
import com.vaadin.navigator.View;
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
import org.vaadin.spring.security.VaadinSecurity;
import javax.annotation.PostConstruct;
import static com.tfkfan.server.ServerUtils.ADMIN_PAGE;

@Theme("Demo")
@SpringUI(path = ADMIN_PAGE)
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

	User currentUser;

	@PostConstruct
	public void init() {
		springNavigator.setErrorView(ErrorView.class);
		springViewProvider.setAccessDeniedViewClass(AccessDeniedView.class);
		springViewDisplay = new Panel();
		springViewDisplay.setSizeFull();
	}

	@Override
	protected void init(VaadinRequest request) {
		getPage().setTitle("Admin module");
		currentUser = SecurityContextUtils.getUser();

		final VerticalLayout root = new VerticalLayout();
		final HeaderBarWidget topElems = new HeaderBarWidget(currentUser, vaadinSecurity);
		topElems.customInit();

		final CssLayout navigationBar = new CssLayout();
		navigationBar.addStyleName(ValoTheme.LAYOUT_COMPONENT_GROUP);
		navigationBar.addComponent(createNavigationButton("Users", AdminUsersView.VIEW_NAME));

		final VerticalLayout gridLayout = new VerticalLayout();
		gridLayout.setSizeFull();

		gridLayout.addComponent(springViewDisplay);
		gridLayout.setExpandRatio(springViewDisplay, 1.0f);

		root.addComponents(topElems, navigationBar, gridLayout);

		setContent(root);
	}

	@Override
	public void showView(View view) {
		springViewDisplay.setContent((Component) view);
	}

	private Button createNavigationButton(String caption, final String viewName) {
		Button button = new Button(caption);
		button.addClickListener(event -> getUI().getNavigator().navigateTo(viewName));
		return button;
	}

}
