package com.example.ui;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.hibernate.Session;

import com.example.constants.Constants;
import com.example.data.DbHelper;
import com.example.data.VocabularyT;
import com.example.data.Words;
import com.vaadin.data.Property;
import com.vaadin.data.util.IndexedContainer;
import com.vaadin.server.VaadinServletService;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.HorizontalSplitPanel;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Table;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;

public class RightContent extends VerticalLayout{

	private static final long serialVersionUID = 1L;
	private Button btnLogOut = new Button(Constants.S_LOG_OUT);

	// vars
	private IndexedContainer wordsContainer;
	private Session session = null;
	private int vocabularyId;

	// Views
	private Table wordsTable = new Table();
	
	
	public RightContent(Session session, int vocabularyId) {
		this.session = session;
		this.vocabularyId = vocabularyId;
		
		if (vocabularyId == -1)	
			return;
		
		
		setRightContentViews();
		initWordsTable();
	}

	@SuppressWarnings("deprecation")
	private void setRightContentViews() {
		
		VocabularyT vocab = DbHelper.getVocabularyById(Integer.toString(vocabularyId), session);
		
		Label lbVocabilaryName = new Label();
		lbVocabilaryName.setContentMode(Label.CONTENT_TEXT);
		lbVocabilaryName.setValue( Constants.S_VOCAB + " : " + vocab.getVocabName());
		
		HorizontalLayout topRightLayout = new HorizontalLayout();
		addComponent(topRightLayout);
		topRightLayout.setWidth("100%");
		topRightLayout.addComponent(lbVocabilaryName);
		topRightLayout.addComponent(btnLogOut);
		topRightLayout.setComponentAlignment(btnLogOut, Alignment.MIDDLE_RIGHT);


		btnLogOut.addClickListener(new ClickListener() {
			private static final long serialVersionUID = 1L;

			@Override
			public void buttonClick(ClickEvent event) {
				session = null;
				String url = RightContent.getApplicationBaseURL(VaadinServletService.getCurrentServletRequest());
				getUI().getPage().setLocation(url);
				getUI().close();
				getUI().push();
			}
		});

		wordsTable.setSizeFull();
		addComponent(wordsTable);
		setExpandRatio(wordsTable, 1);
		setSizeFull();		
	}
	
	private void initWordsTable(){
		
		if (vocabularyId == -1)
			wordsContainer = new IndexedContainer();
		else 
			wordsContainer = createWodsDataSourse();
		
		wordsTable.setContainerDataSource(wordsContainer);
		wordsTable.setVisibleColumns(new String[] { Constants.WORD, Constants.DESCRIPTION, Constants.BTN_DEL_WORD });
		wordsTable.setSelectable(true);
		wordsTable.setImmediate(true);
	}
	
	@SuppressWarnings("unchecked")
	private IndexedContainer createWodsDataSourse() {
		IndexedContainer ic = new IndexedContainer();

		ic.addContainerProperty(Constants.WORD, String.class, "");
		
		ic.addContainerProperty(Constants.DESCRIPTION, String.class, "");
		ic.addContainerProperty(Constants.BTN_DEL_WORD, Button.class, null);
		

		List<Words> words = DbHelper.getWordsByVocabularyId(vocabularyId, session);
		
		for (int i = 0; i < words.size(); i++) {
			
			Words curentWord = words.get(i);
			Object id = ic.addItem();

			wordsTable.setColumnAlignment( Constants.WORD, Table.ALIGN_LEFT);
			wordsTable.setColumnAlignment( Constants.DESCRIPTION, Table.ALIGN_LEFT);
			wordsTable.setColumnAlignment( Constants.BDELETE, Table.ALIGN_CENTER);
			
			ic.getContainerProperty(id, Constants.WORD).setValue(curentWord.getWord());
			ic.getContainerProperty(id, Constants.DESCRIPTION).setValue(curentWord.getDescription());

			Button btnDelete = new Button(Constants.BDELETE);
			btnDelete.setData(curentWord.getId());
			btnDelete.addClickListener(new Button.ClickListener() {
				
				private static final long serialVersionUID = 1L;
				public void buttonClick(ClickEvent event) {

					Integer iid = (Integer) event.getButton().getData();
					
					DbHelper.removeItemByTableName(iid, session, "Words");
					Notification.show(Constants.N_WORD_DEL);
					
					HorizontalSplitPanel splitPanel = UiHelper.getUiHorizontalSplitPanel(session, vocabularyId);
					getUI().setContent(splitPanel);
					getUI().push();		

				}
			});
			
			ic.getContainerProperty(id, Constants.BTN_DEL_WORD).setValue(btnDelete);
		}

		return ic;
	}
	
	public static String getApplicationBaseURL(HttpServletRequest request) {
		return "http" + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
	}
	
}
