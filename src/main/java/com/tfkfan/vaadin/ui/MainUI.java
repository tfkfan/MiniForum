package com.tfkfan.vaadin.ui;

import com.tfkfan.hibernate.entities.Theme;
import com.tfkfan.hibernate.entities.User;
import com.tfkfan.security.SecurityContextUtils;
import com.tfkfan.server.service.impl.ThemeService;
import com.tfkfan.vaadin.ui.widgets.HeaderBarWidget;
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
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import static com.tfkfan.server.ServerUtils.HOME_PAGE;

@com.vaadin.annotations.Theme("Demo")
@SpringUI(path = HOME_PAGE)
public class MainUI extends UI {
	private final static Logger log = Logger.getLogger(MainUI.class.getName());
	private static final long serialVersionUID = 1L;

	@Autowired
	ApplicationContext applicationContext;

	@Autowired
	VaadinSecurity vaadinSecurity;

	@Autowired
	ThemeService themeService;

	User currentUser;

	@PostConstruct
	public void init() {

	}

	@Override
	protected void init(VaadinRequest request) {
		getPage().setTitle("Forum");
		currentUser = SecurityContextUtils.getUser();

		final VerticalLayout root = new VerticalLayout();
		final HeaderBarWidget topElems = new HeaderBarWidget(currentUser, vaadinSecurity);
		topElems.customInit();

		root.setSizeFull();
		root.addComponent(topElems);

		List<Theme> themes = new ArrayList<Theme>();
		try {
			themes.addAll(themeService.getSortedThemes());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		Grid<Theme> grid = new Grid<Theme>();
		grid.setSizeFull();
		grid.setItems(themes);

		grid.addColumn(theme -> "<a href='/theme?id=" + theme.getId() + "' target='_top'>" + theme.getTitle() + "</a>",
				new HtmlRenderer()).setCaption("Title").setSortable(false);
		grid.addColumn(theme -> theme.getAutor().getUsername(), new TextRenderer()).setCaption("Autor").setSortable(false);
		grid.addColumn(Theme::getDate).setCaption("Date").setSortable(false);

		root.addComponent(grid);
		root.setExpandRatio(grid, 1f);

		root.addComponent(new Button("Add Theme", event -> showModalWindow()));

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

		try {
			themeService.addTheme(themeTitle, currentUser);

			subWindow.close();
			Page.getCurrent().reload();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
