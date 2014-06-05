package com.example.ui;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;

import org.hibernate.Session;
import org.mortbay.jetty.security.Credential.MD5;

import com.example.constants.Constants;
import com.example.data.DbHelper;
import com.example.data.VocabularyT;
import com.example.support.MD5Helper;
import com.example.support.VocabularyFileParser;
import com.vaadin.server.Sizeable;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalSplitPanel;
import com.vaadin.ui.Notification;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Button.ClickEvent;

import java.util.Properties;

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

		Button loginButton = new Button(Constants.S_LOGIN,
				new Button.ClickListener() {

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

		holder.setSpacing(true);
		
		holder.setComponentAlignment(username, Alignment.MIDDLE_CENTER);
		holder.setComponentAlignment(password, Alignment.MIDDLE_CENTER);
		holder.setComponentAlignment(loginButton, Alignment.MIDDLE_CENTER);

		addComponent(holder);
		setComponentAlignment(holder, Alignment.MIDDLE_CENTER);

	}

	private void checkUser(String login, String pass) {
		String passwordFromFormMD5 = MD5Helper.getMD5String(pass);

		try {
			Properties properties = VocabularyFileParser.getPropertiesFromClasspath(Constants.CONFIG_FILE_NAME, this.getClass());
			String passwordFromFile = VocabularyFileParser.getPasswordFromProp(properties);
			String loginFromFile = VocabularyFileParser.getLoginFromProp(properties);

			if (login.equals(loginFromFile) && passwordFromFormMD5.equals(passwordFromFile))
				initContent();
			else
				Notification.show("Incorect login or password");
			
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	private void initContent() {
		List<VocabularyT> allVocabularyes = DbHelper
				.getAllVocabularyes(session);

		Integer firstVocabId = null;
		if (allVocabularyes.size() != 0) {
			firstVocabId = allVocabularyes.get(0).getId();
		} else
			firstVocabId = -1;

		HorizontalSplitPanel splitPanel = UiHelper.getUiHorizontalSplitPanel(
				session, firstVocabId);
		getUI().setContent(splitPanel);
		getUI().push();
	}


}
