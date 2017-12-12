package com.tfkfan.vaadin.ui;

import com.tfkfan.hibernate.dao.MessageDao;
import com.tfkfan.hibernate.dao.ThemeDao;
import com.tfkfan.hibernate.entities.Message;
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
import java.util.Set;

@com.vaadin.annotations.Theme("Demo")
@SpringUI(path = "/theme")
public class ThemeUI extends UI {

	private static final long serialVersionUID = 1L;

	@Autowired
	ApplicationContext applicationContext;

	@Autowired
	VaadinSecurity vaadinSecurity;

	@Autowired
	ThemeDao themeDao;
	
	@Autowired
	MessageDao messageDao;
	
	Theme theme;
	User currentUser = SecurityContextUtils.getUser();

	@PostConstruct
	public void init() {

	}

	@Override
	protected void init(VaadinRequest request) {
		getPage().setTitle("Forum ");

		try {
			Long id_theme = Long.parseLong(request.getParameter("id"));
			theme = themeDao.get(id_theme);
			if(theme == null)
				return;
			
			getPage().setTitle(theme.getTitle() + " theme");
			
			Set<Message> messages = theme.getPublishedMessages();

			HorizontalSplitPanel root = new HorizontalSplitPanel();

			final VerticalLayout leftLayout = new VerticalLayout();
			leftLayout.setSizeFull();
			leftLayout.addComponents(
					new Label(SecurityContextUtils.getUser().getUsername() + " : " + LocalDateTime.now()));

			Grid<Message> grid = new Grid<Message>();
			grid.setSizeFull();
			grid.setItems(messages);

			grid.addColumn(message -> message.getText(), new TextRenderer()).setCaption("Text");
			grid.addColumn(message -> message.getUser().getUsername(), new TextRenderer()).setCaption("Autor");
			grid.addColumn(Message::getDate).setCaption("Date");

			leftLayout.addComponent(grid);
			leftLayout.setExpandRatio(grid, 1f);

			root.setFirstComponent(leftLayout);

			VerticalLayout rightLayout = new VerticalLayout();
			Button addBtn = new Button("Add message");
			addBtn.addClickListener(event -> showModalWindow());
			rightLayout.addComponent(addBtn);

			root.setSecondComponent(rightLayout);
			setContent(root);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	protected void showModalWindow() {
		Window subWindow = new Window("Sub-window");
		subWindow.setWidth("30%");
		VerticalLayout subContent = new VerticalLayout();
		subWindow.setContent(subContent);

		FormLayout form = new FormLayout();

		TextField textField = new TextField("Message");
		form.addComponent(textField);

		HorizontalLayout btns = new HorizontalLayout();

		Button formBtn = new Button("Add");
		formBtn.addClickListener(event -> addMessageClick(subWindow, textField.getValue()));

		Button formCloseBtn = new Button("Close");
		formCloseBtn.addClickListener(event -> subWindow.close());
		btns.addComponents(formBtn, formCloseBtn);

		form.addComponent(btns);

		subContent.addComponent(form);
		subWindow.center();
		addWindow(subWindow);
	}

	protected void addMessageClick(Window subWindow, String text) {
		String date = LocalDateTime.now().toString();
		Message message = new Message(text, date, theme, currentUser);
		messageDao.save(message);

		subWindow.close();
		Page.getCurrent().reload();
		
	}
}
