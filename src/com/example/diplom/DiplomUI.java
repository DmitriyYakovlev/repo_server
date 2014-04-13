package com.example.diplom;

import javax.servlet.annotation.WebServlet;

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
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.Table;
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
	private static final String FNAME = "First Name";
	private static final String LNAME = "Last Name";
	private static final String COMPANY = "Company";
	IndexedContainer contactContainer = createDummyDatasource();
	
	// second
	private Table contactList = new Table();

	@Override
	protected void init(VaadinRequest request) {
		//setPaswordFields();
		initLayout();
		initContactList();
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
	
	private void initLayout() {

		HorizontalSplitPanel splitPanel = new HorizontalSplitPanel();
		setContent(splitPanel);

		VerticalLayout leftLayout = new VerticalLayout();
		splitPanel.addComponent(leftLayout);
		
		leftLayout.setSizeFull();
		leftLayout.addComponent(contactList);
		leftLayout.setExpandRatio(contactList, 1);
		contactList.setSizeFull();

	}	
	
	private void initContactList() {
		contactList.setContainerDataSource(contactContainer);
		contactList.setVisibleColumns(new String[] { FNAME, LNAME, COMPANY });
		contactList.setSelectable(true);
		contactList.setImmediate(true);

		contactList.addValueChangeListener(new Property.ValueChangeListener() {

			@Override
			public void valueChange(ValueChangeEvent event) {
				Object contactId = contactList.getValue();
			}
		});
	}

	private static final String[] fieldNames = new String[] { FNAME, LNAME,
		COMPANY, "Mobile Phone", "Work Phone", "Home Phone", "Work Email",
		"Home Email", "Street", "City", "Zip", "State", "Country" };

	private static IndexedContainer createDummyDatasource() {
		IndexedContainer ic = new IndexedContainer();

		for (String p : fieldNames) {
			ic.addContainerProperty(p, String.class, "");
		}

		String[] fnames = { "Peter", "Alice", "Joshua", "Mike", "Olivia",
				"Nina", "Alex", "Rita", "Dan", "Umberto", "Henrik", "Rene",
				"Lisa", "Marge" };
		String[] lnames = { "Smith", "Gordon", "Simpson", "Brown", "Clavel",
				"Simons", "Verne", "Scott", "Allison", "Gates", "Rowling",
				"Barks", "Ross", "Schneider", "Tate" };
		for (int i = 0; i < 1000; i++) {
			Object id = ic.addItem();
			ic.getContainerProperty(id, FNAME).setValue(
					fnames[(int) (fnames.length * Math.random())]);
			ic.getContainerProperty(id, LNAME).setValue(
					lnames[(int) (lnames.length * Math.random())]);
		}

		return ic;
	}
	
}