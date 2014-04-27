package com.example.ui;

import java.util.List;

import org.hibernate.Session;

import com.example.data.DbHelper;
import com.example.data.VocabularyT;
import com.example.data.Words;
import com.vaadin.data.Property;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.util.IndexedContainer;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalSplitPanel;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Table;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Button.ClickEvent;

public class RightSide extends VerticalLayout{

	//// words table
	private static final String WORD = "Word";
	private static final String DESCRIPTION = "Description";
	private static final String BTN_DEL_WORD = "Delete";
	IndexedContainer wordsContainer;
	private Session session = null;
	private int vocabularyId;

	// second
	private Table wordsList = new Table();
	
	public RightSide(Session session, int vocabularyId) {
		this.session = session;
		this.vocabularyId = vocabularyId;
		
		draw();
		initWordsList();
	}

	private void draw() {
		VocabularyT vocab = DbHelper.getVocabularyById(Integer.toString(vocabularyId), session);
		
		Label lbVocabilaryName = new Label("Vocabilary");
		lbVocabilaryName.setContentMode(Label.CONTENT_TEXT);
		lbVocabilaryName.setValue("Vocabilary : " + vocab.getVocabName());
		addComponent(lbVocabilaryName);

		wordsList.setSizeFull();
		addComponent(wordsList);
		setExpandRatio(wordsList, 1);
		setSizeFull();		
	}
	
	private void initWordsList(){
		wordsContainer = createWodsDataSourse();
		wordsList.setContainerDataSource(wordsContainer);
		wordsList.setVisibleColumns(new String[] { WORD, DESCRIPTION, BTN_DEL_WORD });
		wordsList.setSelectable(true);
		wordsList.setImmediate(true);
		
	}
	
	private IndexedContainer createWodsDataSourse() {
		IndexedContainer ic = new IndexedContainer();

		ic.addContainerProperty(WORD, String.class, "");
		ic.addContainerProperty(DESCRIPTION, String.class, "");
		ic.addContainerProperty(BTN_DEL_WORD, Button.class, null);

		List<Words> words = DbHelper.getWordsByVocabularyId(vocabularyId, session);
		

		for (int i = 0; i < words.size(); i++) {
			
			Words curentWord = words.get(i);
			
			Object id = ic.addItem();
			
			
			ic.getContainerProperty(id, WORD).setValue(curentWord.getWord());
			ic.getContainerProperty(id, DESCRIPTION).setValue(curentWord.getDescription());

			Button btnDelete = new Button("Delete");
			btnDelete.setData(curentWord.getId());
			btnDelete.addClickListener(new Button.ClickListener() {
				public void buttonClick(ClickEvent event) {
					Integer iid = (Integer) event.getButton().getData();
					
					DbHelper.removeItemByTableName(iid, session, "Words");
					Notification.show("Word deleted.");
					
					HorizontalSplitPanel splitPanel = UiHelper.setUi(session, vocabularyId);
					System.out.println(Integer.toString(iid)  );

					getUI().setContent(splitPanel);
					getUI().push();
				}
			});
			
			ic.getContainerProperty(id, BTN_DEL_WORD).setValue(btnDelete);
		}

		return ic;
	}
}
