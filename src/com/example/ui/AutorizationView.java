package com.example.ui;

import java.util.List;

import org.hibernate.Session;

import com.example.constants.Constants;
import com.example.data.DbHelper;
import com.example.data.VocabularyT;
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
	
	private Session session = null;
	
	public AutorizationView() {
		session = DbHelper.openSession();
		setAuthorizationFields(this);
	}
	
	public void setAuthorizationFields(VerticalLayout layout) {
		
		setSizeFull();
		setMargin(true);
		setSpacing(true);
		setStyleName(Reindeer.LAYOUT_BLUE);

		username = new TextField(Constants.S_USERNAME);
		password = new PasswordField(Constants.S_PASWORD);

		Button loginButton = new Button(Constants.S_LOGIN, new Button.ClickListener() {
			
			@Override
			public void buttonClick(ClickEvent event) {
				String login = username.getValue();
				String pass = password.getValue();
				
				checkUser(login, pass);
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
	
	private void checkUser(String login, String pass) 
	{
		if (login.equals(Constants.LOGIN) && pass.equals(Constants.PASSWORD) )
			initContent();
		else 
			Notification.show("Incorect login or password");
	}
	
	private void initContent() {
		List<VocabularyT> allVocabularyes = DbHelper.getAllVocabularyes(session);
		
		Integer firstVocabId = null;
		if (allVocabularyes.size() != 0){
			firstVocabId = allVocabularyes.get(0).getId();
		}
		else
			firstVocabId = -1;
							
		HorizontalSplitPanel splitPanel = UiHelper.getUiHorizontalSplitPanel(session, firstVocabId);
		getUI().setContent(splitPanel);
		getUI().push();
	}
}
