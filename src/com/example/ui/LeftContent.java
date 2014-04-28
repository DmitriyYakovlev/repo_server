package com.example.ui;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

import org.hibernate.Session;

import com.example.constants.Constants;
import com.example.data.DbHelper;
import com.example.data.VocabularyT;
import com.example.support.VocabularyFileParser;
import com.vaadin.data.util.IndexedContainer;
import com.vaadin.event.ItemClickEvent;
import com.vaadin.event.ItemClickEvent.ItemClickListener;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.HorizontalSplitPanel;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Table;
import com.vaadin.ui.TextField;
import com.vaadin.ui.Upload;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Upload.FinishedEvent;
import com.vaadin.ui.Upload.Receiver;

public class LeftContent extends VerticalLayout {

	// Views
	private TextField tfNewVocabulary = new TextField();
	private Button btnAddVocab = new Button(Constants.S_ADD_VOCAB);
	private Table vocabularyTable = new Table();

	// Vars
	private List<VocabularyT> allVocabularyes;
	private IndexedContainer vocabContainer;
	private static LoadVocabularyFileReceiver receiver = new LoadVocabularyFileReceiver();
	private static File tempVocabFile;
	private Session session = null;

	public LeftContent(Session session) {
		this.session = session;
		setLeftContentViews();
		initVocabilaryTable();
	}

	@SuppressWarnings("deprecation")
	private void setLeftContentViews() {
		Label lbUser = new Label();
		lbUser.setContentMode(Label.CONTENT_TEXT);
		lbUser.setValue(Constants.S_USER + " : " + Constants.LOGIN);
		addComponent(lbUser);

		addComponent(vocabularyTable);
		setExpandRatio(vocabularyTable, 1);
		vocabularyTable.setSizeFull();

		HorizontalLayout bottomLeftLayout = new HorizontalLayout();
		addComponent(bottomLeftLayout);
		bottomLeftLayout.addComponent(tfNewVocabulary);
		bottomLeftLayout.addComponent(btnAddVocab);

		btnAddVocab.addClickListener(new ClickListener() {
			private static final long serialVersionUID = 1L;

			@Override
			public void buttonClick(ClickEvent event) {
				String vocabName = tfNewVocabulary.getValue().toString();
				onAddVocabPress(vocabName);
			}
		});

		setSizeFull();
	}

	private void onAddVocabPress(String vocabName) {
		// TODO : oбробка ввода і захист від дурня.
		if (vocabName.equals("") || vocabName == null) {

		} else {
			VocabularyT nVocab = new VocabularyT(vocabName, "my vocabulary", 1);
			DbHelper.addVocabulary(nVocab, session);
			initVocabilaryTable();
		}
	}

	private void initVocabilaryTable() {
		vocabContainer = createVocabilaryDatasource();

		vocabularyTable.setContainerDataSource(vocabContainer);
		vocabularyTable.setVisibleColumns(new String[] { Constants.VNAME,
				Constants.BLOAD, Constants.BDELETE });
		vocabularyTable.setSelectable(true);
		vocabularyTable.setImmediate(true);

		vocabularyTable.addItemClickListener(new ItemClickListener() {
			@Override
			public void itemClick(ItemClickEvent event) {
				Integer id = (Integer) event.getItemId();
				Integer trueId = allVocabularyes.get(id - 1).getId();
				setLeftContent(trueId);
			}
		});
	}

	@SuppressWarnings({ "deprecation", "unchecked" })
	public IndexedContainer createVocabilaryDatasource() {
		IndexedContainer idnxContain = new IndexedContainer();

		idnxContain.addContainerProperty(Constants.VNAME, String.class, "");
		idnxContain.addContainerProperty(Constants.BLOAD, Upload.class, null);
		idnxContain.addContainerProperty(Constants.BDELETE, Button.class, null);

		allVocabularyes = DbHelper.getAllVocabularyes(session);

		for (int i = 0; i < allVocabularyes.size(); i++) {

			VocabularyT curentVoc = allVocabularyes.get(i);

			Object id = idnxContain.addItem();
			idnxContain.getContainerProperty(id, Constants.VNAME).setValue(
					curentVoc.getVocabName());

			Upload btnLoad = new Upload(null, receiver);
			btnLoad.setData(curentVoc.getId());
			btnLoad.setImmediate(true);
			btnLoad.setButtonCaption(Constants.S_SELECT_FILE);
			btnLoad.addListener(new Upload.FinishedListener() {
				private static final long serialVersionUID = 1L;
				public void uploadFinished(FinishedEvent event) {
					Integer vocabId = (Integer) event.getUpload().getData();
					onUploadFinished(vocabId);
				}
			});
			idnxContain.getContainerProperty(id, Constants.BLOAD).setValue(
					btnLoad);

			Button btnDelete = new Button(Constants.BDELETE);
			btnDelete.setData(curentVoc.getId());
			btnDelete.addClickListener(new Button.ClickListener() {
				private static final long serialVersionUID = 1L;
				public void buttonClick(ClickEvent event) {
					Integer vocabId = (Integer) event.getButton().getData();
					onDeleteVocab(vocabId);
				}
			});
			idnxContain.getContainerProperty(id, Constants.BDELETE).setValue(
					btnDelete);

		}
		return idnxContain;

	}

	private void onDeleteVocab(Integer vocabId) {
		DbHelper.removeItemByTableName(vocabId, session, "VocabularyT");
		Notification.show(Constants.N_VOCAB_DEL);
		setLeftContent(1);
	}

	private void onUploadFinished(Integer vocabId) {
		VocabularyFileParser.parseVocabFile(tempVocabFile, vocabId, session);
		setLeftContent(vocabId);
	}
	
	private void setLeftContent(Integer vocabId) {
		HorizontalSplitPanel splitPanel = UiHelper.getUiHorizontalSplitPanel(
				session, vocabId);
		getUI().setContent(splitPanel);
		getUI().push();
	}

	public static class LoadVocabularyFileReceiver implements Receiver {

		private static final long serialVersionUID = 1L;

		public OutputStream receiveUpload(String filename, String mimetype) {
			try {
				tempVocabFile = File.createTempFile("temp", ".txt");
				return new FileOutputStream(tempVocabFile);
			} catch (IOException e) {
				e.printStackTrace();
				return null;
			}
		}

	}
}
