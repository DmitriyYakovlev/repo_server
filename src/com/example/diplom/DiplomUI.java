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
import com.vaadin.ui.Notification;
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
	private TextField searchField = new TextField();
	private Button addNewContactButton = new Button("New");
	private VerticalLayout rightLayout = new VerticalLayout();

	//// vocabilary table
	private static final String VNAME = "Vocabilary name";
	private static final String BLOAD = "Load";
	private static final String BDELETE = "Delete";
	IndexedContainer contactContainer = createVocabilaryDatasource();
	
	//// words table
	private static final String WORD = "Word";
	private static final String DESCRIPTION = "Description";
	private static final String BTN_DEL_WORD = "Delete";
	IndexedContainer wordsContainer = createWodsDataSourse();

	

	// second
	private Table vocabilaryList = new Table();
	private Table wordsList = new Table();

	@Override
	protected void init(VaadinRequest request) {
		// setPaswordFields();
		initLayout();
		initVocabilaryList();
		initWordsList();
	}

	public void setPaswordFields() {
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
		splitPanel.addComponent(rightLayout);

		Label lbUser = new Label("User :");
		lbUser.setContentMode(Label.CONTENT_TEXT);
		leftLayout.addComponent(lbUser);

		Label lbUserName = new Label("___");
		lbUserName.setContentMode(Label.CONTENT_TEXT);
		leftLayout.addComponent(lbUser);

		leftLayout.setSizeFull();
		leftLayout.addComponent(vocabilaryList);
		leftLayout.setExpandRatio(vocabilaryList, 1);
		vocabilaryList.setSizeFull();
		
		HorizontalLayout bottomLeftLayout = new HorizontalLayout();
		leftLayout.addComponent(bottomLeftLayout);
		bottomLeftLayout.addComponent(searchField);
		bottomLeftLayout.addComponent(addNewContactButton);

		//////////
		Label lbVocabilaryName = new Label("Vocabilary");
		lbVocabilaryName.setContentMode(Label.CONTENT_TEXT);
		rightLayout.addComponent(lbVocabilaryName);

		
		rightLayout.addComponent(wordsList);
		rightLayout.setExpandRatio(wordsList, 1);
		wordsList.setSizeFull();

	}

	private void initVocabilaryList() {
		vocabilaryList.setContainerDataSource(contactContainer);
		vocabilaryList.setVisibleColumns(new String[] { VNAME, BLOAD, BDELETE });
		vocabilaryList.setSelectable(true);
		vocabilaryList.setImmediate(true);

		vocabilaryList.addValueChangeListener(new Property.ValueChangeListener() {

			@Override
			public void valueChange(ValueChangeEvent event) {
				Object contactId = vocabilaryList.getValue();
			}
		});
		
	}

	private void initWordsList(){
		wordsList.setContainerDataSource(wordsContainer);
		wordsList.setVisibleColumns(new String[] { WORD, DESCRIPTION, BTN_DEL_WORD });
		wordsList.setSelectable(true);
		wordsList.setImmediate(true);

		wordsList.addValueChangeListener(new Property.ValueChangeListener() {

			@Override
			public void valueChange(ValueChangeEvent event) {
				Object contactId = wordsList.getValue();
			}
		});
		
	}
	
	private static IndexedContainer createVocabilaryDatasource() {
		IndexedContainer ic = new IndexedContainer();

		ic.addContainerProperty(VNAME, String.class, "");
		ic.addContainerProperty(BLOAD, Button.class, null);
		ic.addContainerProperty(BDELETE, Button.class, null);

		String[] fnames = { "Peter", "Alice", "Joshua", "Mike", "Olivia",
				"Nina", "Alex", "Rita", "Dan", "Umberto", "Henrik", "Rene",
				"Lisa", "Marge" };

		for (int i = 0; i < 1000; i++) {
			Object id = ic.addItem();
			ic.getContainerProperty(id, VNAME).setValue(fnames[(int) (fnames.length * Math.random())]);

			Integer itemId = new Integer(i);

			Button btnLoad = new Button("load");
			btnLoad.setData(itemId);
			btnLoad.addClickListener(new Button.ClickListener() {
				public void buttonClick(ClickEvent event) {
					Integer iid = (Integer) event.getButton().getData();
					Notification.show("Link " + iid.intValue() + " clicked.");
				}
			});
			ic.getContainerProperty(id, BLOAD).setValue(btnLoad);
			
			Button btnDelete = new Button("Delete");
			btnDelete.setData(itemId);
			btnDelete.addClickListener(new Button.ClickListener() {
				public void buttonClick(ClickEvent event) {
					Integer iid = (Integer) event.getButton().getData();
					Notification.show("Link " + iid.intValue() + " clicked.");
				}
			});
			ic.getContainerProperty(id, BDELETE).setValue(btnDelete);

		}

		return ic;
	}

	
	private static IndexedContainer createWodsDataSourse() {
		IndexedContainer ic = new IndexedContainer();

		ic.addContainerProperty(WORD, String.class, "");
		ic.addContainerProperty(DESCRIPTION, String.class, "");
		ic.addContainerProperty(BTN_DEL_WORD, Button.class, null);

		String[] fnames = { "Peter", "Alice", "Joshua", "Mike", "Olivia",
				"Nina", "Alex", "Rita", "Dan", "Umberto", "Henrik", "Rene",
				"Lisa", "Marge" };

		for (int i = 0; i < 1000; i++) {
			Object id = ic.addItem();
			ic.getContainerProperty(id, WORD).setValue(fnames[(int) (fnames.length * Math.random())]);

			ic.getContainerProperty(id, DESCRIPTION).setValue(fnames[(int) (fnames.length * Math.random())]);

			Integer itemId = new Integer(i);

			Button btnDelete = new Button("Delete");
			btnDelete.setData(itemId);
			btnDelete.addClickListener(new Button.ClickListener() {
				public void buttonClick(ClickEvent event) {
					Integer iid = (Integer) event.getButton().getData();
					Notification.show("Link " + iid.intValue() + " clicked.");
				}
			});
			ic.getContainerProperty(id, BTN_DEL_WORD).setValue(btnDelete);

		}

		return ic;
	}
}