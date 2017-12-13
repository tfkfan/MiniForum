package com.tfkfan.vaadin.ui;

import com.tfkfan.hibernate.dao.ThemeDao;
import com.tfkfan.hibernate.entities.Theme;
import com.tfkfan.hibernate.entities.User;
import com.tfkfan.security.SecurityContextUtils;
import com.tfkfan.vaadin.ui.widgets.HeadUserWidget;
import com.vaadin.server.Page;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.*;
import com.vaadin.ui.renderers.HtmlRenderer;
import com.vaadin.ui.renderers.TextRenderer;
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

	User currentUser;

	@PostConstruct
	public void init() {

	}

	@Override
	protected void init(VaadinRequest request) {
		getPage().setTitle("Forum");
		currentUser = SecurityContextUtils.getUser();

		final VerticalLayout root = new VerticalLayout();
		final HeadUserWidget topElems = new HeadUserWidget(currentUser, vaadinSecurity);
		topElems.customInit();

		root.setSizeFull();
		root.addComponent(topElems);

		List<Theme> themes = themeDao.listAll();

		Grid<Theme> grid = new Grid<Theme>();
		grid.setSizeFull();
		grid.setItems(themes);

		grid.addColumn(theme -> "<a href='/theme?id=" + theme.getId() + "' target='_top'>" + theme.getTitle() + "</a>",
				new HtmlRenderer()).setCaption("Title");
		grid.addColumn(theme -> theme.getAutor().getUsername(), new TextRenderer()).setCaption("Autor");
		grid.addColumn(Theme::getDate).setCaption("Date");

		root.addComponent(grid);
		root.setExpandRatio(grid, 1f);

		setContent(root);
	}

	protected void showModalWindow() {
		Window subWindow = new Window("Sub-window");
		subWindow.setWidth("30%");
		VerticalLayout subContent = new VerticalLayout();
		subWindow.setContent(subContent);

		FormLayout form = new FormLayout();

		TextField titleField = new TextField("Theme title");
		form.addComponent(titleField);

		HorizontalLayout btns = new HorizontalLayout();

		Button formBtn = new Button("Create");
		formBtn.addClickListener(event -> addThemeClick(subWindow, titleField.getValue()));

		Button formCloseBtn = new Button("Close");
		formCloseBtn.addClickListener(event -> subWindow.close());
		btns.addComponents(formBtn, formCloseBtn);

		form.addComponent(btns);

		subContent.addComponent(form);
		subWindow.center();
		addWindow(subWindow);
	}

	protected void addThemeClick(Window subWindow, String themeTitle) {
		User currentUser = SecurityContextUtils.getUser();
		Theme theme = new Theme(LocalDateTime.now().toString(), themeTitle, currentUser);

		themeDao.save(theme);

		subWindow.close();
		Page.getCurrent().reload();
	}
}
