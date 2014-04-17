package com.example.diplom;

import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.annotation.WebServlet;

import com.example.ui.AutorizationView;
import com.example.ui.LeftSide;
import com.example.ui.RightSide;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.data.Property;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.util.IndexedContainer;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.HorizontalSplitPanel;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.Table;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.Upload;
import com.vaadin.ui.Upload.FinishedEvent;
import com.vaadin.ui.Upload.Receiver;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.Reindeer;

@SuppressWarnings("serial")
@Theme("diplom")
public class DiplomUI extends UI {

	@WebServlet(value = "/*", asyncSupported = true)
	@VaadinServletConfiguration(productionMode = false, ui = DiplomUI.class)
	public static class Servlet extends VaadinServlet { }

	@Override
	protected void init(VaadinRequest request) {
		
		AutorizationView autorization = new AutorizationView();
		setContent(autorization);
int i;
//		initLayout();

	}

	private void initLayout() {

		HorizontalSplitPanel splitPanel = new HorizontalSplitPanel();
		setContent(splitPanel);

		LeftSide leftLayout = new LeftSide();
		RightSide rightLayout = new RightSide();
		
		splitPanel.addComponent(leftLayout);
		splitPanel.addComponent(rightLayout);


	}




	
		
}