package com.example.diplom;


import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

public class EditFields extends VerticalLayout {

    private  TextField username;
    private  PasswordField password;

    public void TextFieldSecretExample() {
        setSizeUndefined(); // let content 'push' size
        setSpacing(true);

        // Username
        username = new TextField("Username");
        addComponent(username);

        // Password
        password = new PasswordField("Password");
        addComponent(password);

        // Login button
        Button loginButton = new Button("Login", new Button.ClickListener() {
        	
			@Override
			public void buttonClick(ClickEvent event) {

				
			}
        });
        addComponent(loginButton);
        setComponentAlignment(loginButton, Alignment.TOP_RIGHT);

    }
}