package com.example.ui;

import com.example.constants.Constants;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Notification;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.themes.Reindeer;

public class AutorizationView extends VerticalLayout{

	private TextField username;
	private PasswordField password;
	
	public AutorizationView() {
		setPaswordFields(this);
	}
	
	public void setPaswordFields(VerticalLayout layout) {
		setMargin(true);
		setSpacing(true);
		setStyleName(Reindeer.LAYOUT_BLUE);

		username = new TextField("Username");
		addComponent(username);
		setComponentAlignment(username, Alignment.MIDDLE_CENTER);

		password = new PasswordField("Password");
		addComponent(password);
		setComponentAlignment(password, Alignment.MIDDLE_CENTER);

		Button loginButton = new Button("Login", new Button.ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {
				String login = username.getValue();
				String pass = password.getValue();
				
				if (login.equals(Constants.LOGIN) && pass.equals(Constants.PASSWORD))
				{
					
				}
				else {
					Notification.show("Incorect login or password");
					
				}
			}
		});

		addComponent(loginButton);
		setComponentAlignment(loginButton, Alignment.MIDDLE_CENTER);
		
	}
	
}
