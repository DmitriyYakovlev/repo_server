package com.example.diplom;

import javax.servlet.annotation.WebServlet;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Label;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.Reindeer;

@SuppressWarnings("serial")
@Theme("diplom")
public class DiplomUI extends UI {

	@WebServlet(value = "/*", asyncSupported = true)
	@VaadinServletConfiguration(productionMode = false, ui = DiplomUI.class)
	public static class Servlet extends VaadinServlet {
	}

	private TextField username;
	private PasswordField password;

	@Override
	protected void init(VaadinRequest request) {
		setPaswordFields();
	}

	public void setPaswordFields(){
		VerticalLayout layout = new VerticalLayout();
		layout.setMargin(true);
		layout.setSpacing(true);
		layout.setStyleName(Reindeer.LAYOUT_BLUE);
			
		username = new TextField("Username");
		layout.addComponent(username);
		layout.setComponentAlignment(username, Alignment.MIDDLE_CENTER);


		password = new PasswordField("Password");
		layout.addComponent(password);
		layout.setComponentAlignment(password, Alignment.MIDDLE_CENTER);


		Button loginButton = new Button("Login", new Button.ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {
				
			}
		});
		
		layout.addComponent(loginButton);
		layout.setComponentAlignment(loginButton, Alignment.MIDDLE_CENTER);
		setContent(layout);

	}
	
	
}