package com.example.ui;

import com.example.constants.Constants;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalSplitPanel;
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
		
		setSizeFull();

		setMargin(true);
		setSpacing(true);
		setStyleName(Reindeer.LAYOUT_BLUE);

		username = new TextField("Username");
		password = new PasswordField("Password");

		Button loginButton = new Button("Login", new Button.ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {
				String login = username.getValue();
				String pass = password.getValue();
				
				if (login.equals(Constants.LOGIN) && pass.equals(Constants.PASSWORD))
				{
					HorizontalSplitPanel splitPanel = new HorizontalSplitPanel();

					LeftSide leftLayout = new LeftSide();
					RightSide rightLayout = new RightSide();
					
					splitPanel.addComponent(leftLayout);
					splitPanel.addComponent(rightLayout);
					
					getUI().setContent(splitPanel);
					getUI().push();
				}
				else {
					Notification.show("Incorect login or password");
					
				}
			}
		});

		VerticalLayout holder = new VerticalLayout();
		holder.addComponent(username);
		holder.addComponent(password);
		holder.addComponent(loginButton);
		
		holder.setComponentAlignment(username, Alignment.MIDDLE_CENTER);
		holder.setComponentAlignment(password, Alignment.MIDDLE_CENTER);
		holder.setComponentAlignment(loginButton, Alignment.MIDDLE_CENTER);
		
		addComponent(holder);
		setComponentAlignment(holder, Alignment.MIDDLE_CENTER);
		
		
	}
	
}
