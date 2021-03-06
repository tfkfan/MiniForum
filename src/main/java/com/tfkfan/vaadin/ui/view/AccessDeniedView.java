package com.tfkfan.vaadin.ui.view;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

import javax.annotation.PostConstruct;

@SpringComponent
@UIScope
@SpringView
public class AccessDeniedView extends VerticalLayout implements View {
	private static final long serialVersionUID = 1L;

	@Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
    }

    @PostConstruct
    public void init() {
        Label label = new Label("this is AccessDeniedView.");
        label.addStyleName(ValoTheme.LABEL_FAILURE);
        addComponent(label);
    }
}
