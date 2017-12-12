package com.tfkfan.vaadin.ui;

import com.tfkfan.hibernate.dao.ThemeDao;
import com.tfkfan.hibernate.entities.Theme;
import com.tfkfan.hibernate.entities.User;
import com.tfkfan.security.SecurityContextUtils;
import com.tfkfan.vaadin.ui.view.AccessDeniedView;
import com.tfkfan.vaadin.ui.view.AdminUsersView;
import com.tfkfan.vaadin.ui.view.ErrorView;
import com.tfkfan.vaadin.ui.view.UserView;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewDisplay;
import com.vaadin.server.Page;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.spring.annotation.SpringViewDisplay;
import com.vaadin.spring.navigator.SpringNavigator;
import com.vaadin.spring.navigator.SpringViewProvider;
import com.vaadin.ui.*;
import com.vaadin.ui.renderers.ButtonRenderer;
import com.vaadin.ui.renderers.HtmlRenderer;
import com.vaadin.ui.renderers.TextRenderer;
import com.vaadin.ui.themes.ValoTheme;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.vaadin.spring.security.VaadinSecurity;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.util.List;

@com.vaadin.annotations.Theme("Demo")
@SpringUI(path = "/")
public class MainUI extends UI {

	private static final long serialVersionUID = 1L;

	@Autowired
	ApplicationContext applicationContext;

	@Autowired
	VaadinSecurity vaadinSecurity;

	@Autowired
	ThemeDao themeDao;

	@PostConstruct
	public void init() {

	}

	@Override
	protected void init(VaadinRequest request) {
		getPage().setTitle("Forum");

		HorizontalSplitPanel root = new HorizontalSplitPanel();

		final VerticalLayout leftLayout = new VerticalLayout();
		leftLayout.setSizeFull();
		leftLayout.addComponents(new Label(SecurityContextUtils.getUser().getUsername() + " : " + LocalDateTime.now()));

		List<Theme> themes = themeDao.listAll();

		Grid<Theme> grid = new Grid<>();
		grid.setSizeFull();
		grid.setItems(themes);

		grid.addColumn(theme -> "<a href='/" + theme.getId() + "' target='_top'>" + theme.getTitle() + "</a>",
				new HtmlRenderer()).setCaption("Title");
		grid.addColumn(theme -> theme.getAutor().getUsername(), new TextRenderer()).setCaption("Autor");
		grid.addColumn(Theme::getDate).setCaption("Date");

		leftLayout.addComponent(grid);
		leftLayout.setExpandRatio(grid, 1f);

		root.setFirstComponent(leftLayout);

		VerticalLayout rightLayout = new VerticalLayout();
		Button addBtn = new Button("Add Theme");
		addBtn.addClickListener(event -> showModalWindow());
		rightLayout.addComponent(addBtn);

		root.setSecondComponent(rightLayout);
		setContent(root);
	}

	protected void showModalWindow() {
		Window subWindow = new Window("Sub-window");
		subWindow.setWidth("50%");
		VerticalLayout subContent = new VerticalLayout();
		subWindow.setContent(subContent);

		FormLayout form = new FormLayout();

		TextField titleField = new TextField("Theme title");
		form.addComponent(titleField);

		Button formBtn = new Button("Create");
		formBtn.addClickListener(event -> addThemeClick(subWindow, titleField.getValue()));

		Button formCloseBtn = new Button("Close");
		formCloseBtn.addClickListener(event -> subWindow.close());

		form.addComponent(formBtn);
		form.addComponent(formCloseBtn);

		subContent.addComponent(form);
		subWindow.center();
		addWindow(subWindow);
	}

	protected void addThemeClick(Window subWindow, String themeTitle) {
		Theme theme = new Theme();
		User currentUser = SecurityContextUtils.getUser();
		theme.setAutor(currentUser);
		theme.setTitle(themeTitle);
		theme.setDate(LocalDateTime.now().toString());

		themeDao.save(theme);

		subWindow.close();
		Page.getCurrent().reload();
	}
}
