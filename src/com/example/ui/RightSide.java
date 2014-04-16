package com.example.ui;

import com.vaadin.data.Property;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.util.IndexedContainer;
import com.vaadin.event.MouseEvents.ClickEvent;
import com.vaadin.ui.Button;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Table;
import com.vaadin.ui.VerticalLayout;

public class RightSide extends VerticalLayout{

	//// words table
	private static final String WORD = "Word";
	private static final String DESCRIPTION = "Description";
	private static final String BTN_DEL_WORD = "Delete";
	IndexedContainer wordsContainer = createWodsDataSourse();

	// second
	private Table wordsList = new Table();
	
	public RightSide() {
		
		draw();
		initWordsList();
	}

	private void draw() {
		Label lbVocabilaryName = new Label("Vocabilary");
		lbVocabilaryName.setContentMode(Label.CONTENT_TEXT);
		addComponent(lbVocabilaryName);

		wordsList.setSizeFull();
		addComponent(wordsList);
		setExpandRatio(wordsList, 1);
		setSizeFull();		
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
				
				@Override
				public void buttonClick(com.vaadin.ui.Button.ClickEvent event) {
					Integer iid = (Integer) event.getButton().getData();
					Notification.show("Link " + iid.intValue() + " clicked.");					
				}
			});
			ic.getContainerProperty(id, BTN_DEL_WORD).setValue(btnDelete);
		}

		return ic;
	}
}
