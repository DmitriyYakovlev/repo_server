package com.example.diplom;


import javax.servlet.annotation.WebServlet;

import com.example.ui.AutorizationView;
import com.vaadin.annotations.PreserveOnRefresh;
import com.vaadin.annotations.Push;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.UI;

@SuppressWarnings("serial")
@Theme("diplom")
@PreserveOnRefresh
@Push
public class DiplomUI extends UI {

	@WebServlet(urlPatterns = {"/ui/*", "/VAADIN/*"}, asyncSupported = true)
	@VaadinServletConfiguration(productionMode = false, ui = DiplomUI.class)
	public static class Servlet extends VaadinServlet { }


	@Override
	protected void init(VaadinRequest request) 
	{
		AutorizationView autorization = new AutorizationView();
		setContent(autorization);
		
		
	}

}