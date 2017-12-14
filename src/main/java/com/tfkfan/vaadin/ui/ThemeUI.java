package com.tfkfan.vaadin.ui;

import com.tfkfan.hibernate.dao.MessageDao;
import com.tfkfan.hibernate.dao.ThemeDao;
import com.tfkfan.hibernate.entities.Message;
import com.tfkfan.hibernate.entities.Theme;
import com.tfkfan.hibernate.entities.User;
import com.tfkfan.security.SecurityContextUtils;
import com.tfkfan.server.RequestMaker;
import com.tfkfan.server.service.dto.MessageDto;
import com.tfkfan.server.service.dto.ThemeDto;
import com.tfkfan.vaadin.ui.widgets.HeaderBarWidget;
import com.vaadin.server.Page;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.*;
import com.vaadin.ui.renderers.TextRenderer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.vaadin.spring.security.VaadinSecurity;
import javax.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static com.tfkfan.server.ServerUtils.HOME_PAGE;
import static com.tfkfan.server.ServerUtils.THEME_PAGE;

@com.vaadin.annotations.Theme("Demo")
@SpringUI(path = THEME_PAGE)
public class ThemeUI extends UI {

	private static final long serialVersionUID = 1L;

	@Autowired
	ApplicationContext applicationContext;

	@Autowired
	VaadinSecurity vaadinSecurity;

	private RequestMaker<ThemeDto> rm;
	private RequestMaker<MessageDto> msgRm;

	ThemeDto theme;
	User currentUser;

	@PostConstruct
	public void init() {
		rm = new RequestMaker<ThemeDto>(ThemeDto.class);
		msgRm = new RequestMaker<MessageDto>(MessageDto.class);
	}

	@Override
	protected void init(VaadinRequest request) {
		try {
			getPage().setTitle("Forum theme");
			currentUser = SecurityContextUtils.getUser();

			String param = request.getParameter("id");
			if (param == null) {
				getPage().setLocation(HOME_PAGE);
				return;
			}
			
			Long id_theme = Long.parseLong(param);

			theme = rm.get("/themes/" + id_theme);

			Set<MessageDto> messages = new HashSet<MessageDto>();
			if (theme != null) {
				getPage().setTitle(theme.getTitle() + " theme");
				messages.addAll(
						msgRm.get("/messages/" + theme.getId(), new ParameterizedTypeReference<List<MessageDto>>() {
						}));
			}

			final VerticalLayout root = new VerticalLayout();
			final HeaderBarWidget topElems = new HeaderBarWidget(currentUser, vaadinSecurity);
			topElems.customInit();

			root.setSizeFull();
			root.addComponent(topElems);

			Grid<MessageDto> grid = new Grid<MessageDto>();
			grid.setSizeFull();
			grid.setItems(messages);

			grid.addColumn(message -> message.getText(), new TextRenderer()).setCaption("Text").setWidth(1000);
			grid.addColumn(message -> message.getUser(), new TextRenderer()).setCaption("Autor");
			grid.addColumn(MessageDto::getDate).setCaption("Date");

			root.addComponent(grid);
			root.setExpandRatio(grid, 1f);

			final HorizontalLayout bottomElems = new HorizontalLayout();
			bottomElems.setWidth("100%");

			FormLayout form = createMessageForm();
			bottomElems.addComponent(form);
			bottomElems.setComponentAlignment(form, Alignment.BOTTOM_RIGHT);

			root.addComponent(bottomElems);
			setContent(root);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	protected FormLayout createMessageForm() {
		FormLayout form = new FormLayout();
		VerticalLayout layout = new VerticalLayout();

		layout.setWidth("100%");

		TextArea textField = new TextArea("Message");
		textField.setWidth("100%");
		layout.addComponent(textField);

		Button formBtn = new Button("Add message");
		formBtn.addClickListener(event -> addMessageClick(textField.getValue()));
		layout.addComponent(formBtn);
		layout.setComponentAlignment(formBtn, Alignment.BOTTOM_RIGHT);

		form.addComponent(layout);
		return form;
	}

	protected void addMessageClick(String text) {
		if (theme == null)
			return;

		msgRm.put(new HttpEntity<MessageDto>(new MessageDto(theme.getId(), currentUser.getId(), text)),
				"/messages/add");

		Page.getCurrent().reload();

	}
}
