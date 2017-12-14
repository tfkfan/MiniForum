package com.tfkfan.vaadin.ui;

import com.tfkfan.hibernate.entities.Message;
import com.tfkfan.hibernate.entities.User;
import com.tfkfan.security.SecurityContextUtils;
import com.tfkfan.server.service.impl.MessageService;
import com.tfkfan.vaadin.ui.widgets.HeaderBarWidget;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.*;
import com.vaadin.ui.renderers.ButtonRenderer;
import com.vaadin.ui.renderers.ClickableRenderer.RendererClickEvent;
import com.vaadin.ui.renderers.HtmlRenderer;
import com.vaadin.ui.renderers.TextRenderer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.vaadin.spring.security.VaadinSecurity;
import javax.annotation.PostConstruct;
import java.util.List;
import static com.tfkfan.server.ServerUtils.MODER_PAGE;

@com.vaadin.annotations.Theme("Demo")
@SpringUI(path = MODER_PAGE)
public class ModeratorUI extends UI {

	private static final long serialVersionUID = 1L;

	@Autowired
	ApplicationContext applicationContext;

	@Autowired
	VaadinSecurity vaadinSecurity;

	@Autowired
	MessageService msgService;

	User currentUser = SecurityContextUtils.getUser();
	List<Message> messages;

	@PostConstruct
	public void init() {

	}

	@Override
	protected void init(VaadinRequest request) {
		getPage().setTitle("Forum moderator page");

		try {
			final VerticalLayout root = new VerticalLayout();
			final HeaderBarWidget topElems = new HeaderBarWidget(currentUser, vaadinSecurity);
			topElems.customInit();

			root.addComponent(topElems);

			messages = msgService.listNotPublished();
			Grid<Message> grid = new Grid<Message>();
			grid.setSizeFull();
			grid.setItems(messages);

			grid.addColumn(message -> "<a href='/theme?id=" + message.getTheme().getId() + "' target='_top'>"
					+ message.getTheme().getTitle() + "</a>", new HtmlRenderer()).setCaption("Theme");
			grid.addColumn(message -> message.getText(), new TextRenderer()).setCaption("Text");
			grid.addColumn(message -> message.getUser().getUsername(), new TextRenderer()).setCaption("Autor");
			grid.addColumn(Message::getDate).setCaption("Date");
			grid.addColumn(message -> "Publish message",
					new ButtonRenderer<Message>(clickEvent -> publishClick(clickEvent, grid))).setCaption("Publish");

			root.addComponent(grid);
			root.setExpandRatio(grid, 1f);

			setContent(root);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	protected void publishClick(RendererClickEvent<Message> clickEvent, Grid<Message> grid) {
		Message msg = clickEvent.getItem();

		try {
			msgService.publish(msg);

			messages.remove(msg);
			grid.clearSortOrder();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
