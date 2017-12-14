package com.tfkfan.vaadin.ui;

import com.tfkfan.hibernate.entities.User;
import com.tfkfan.security.SecurityContextUtils;
import com.tfkfan.server.RequestMaker;
import com.tfkfan.server.service.dto.MessageDto;
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
import org.springframework.core.ParameterizedTypeReference;
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

	User currentUser = SecurityContextUtils.getUser();
	List<MessageDto> messages;
	
	private RequestMaker<MessageDto> rm;

	@PostConstruct
	public void init() {
		rm = new RequestMaker<MessageDto>(MessageDto.class);
	}

	@Override
	protected void init(VaadinRequest request) {
		getPage().setTitle("Forum moderator page");

		try {
			final VerticalLayout root = new VerticalLayout();
			final HeaderBarWidget topElems = new HeaderBarWidget(currentUser, vaadinSecurity);
			topElems.customInit();

			root.addComponent(topElems);


			messages = rm.get("/messages/not_published", new ParameterizedTypeReference<List<MessageDto>>() {});
			Grid<MessageDto> grid = new Grid<MessageDto>();

			grid.setSizeFull();
			grid.setItems(messages);

			grid.addColumn(message -> "<a href='/theme?id=" + message.getId_theme() + "' target='_top'>"
					+ message.getTheme_title() + "</a>", new HtmlRenderer()).setCaption("Theme");
			grid.addColumn(message -> message.getText(), new TextRenderer()).setCaption("Text");
			grid.addColumn(message -> message.getUser(), new TextRenderer()).setCaption("Autor");
			grid.addColumn(MessageDto::getDate).setCaption("Date");
			grid.addColumn(message -> "Publish message",
					new ButtonRenderer<MessageDto>(clickEvent -> publishClick(clickEvent, grid))).setCaption("Publish");

			root.addComponent(grid);
			root.setExpandRatio(grid, 1f);

			setContent(root);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	protected void publishClick(RendererClickEvent<MessageDto> clickEvent, Grid<MessageDto> grid) {
		MessageDto msg = clickEvent.getItem();
		msg.setIs_published(true);
		rm.post(null, "/messages/publish/" + msg.getId());
		messages.remove(msg);
		grid.clearSortOrder();

	}
}
