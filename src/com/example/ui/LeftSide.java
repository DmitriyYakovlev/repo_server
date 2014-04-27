package com.example.ui;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.hibernate.Session;

import com.example.constants.Constants;
import com.example.data.DbHelper;
import com.example.data.VocabularyT;
import com.example.data.Words;
import com.vaadin.data.Property;
import com.vaadin.data.Property.ValueChangeEvent;
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

public class LeftSide extends VerticalLayout {

	private TextField searchField = new TextField();
	private Button addNewContactButton = new Button("New");
	private Table vocabilaryList = new Table();

	// // vocabilary table
	private static final String VNAME = "Vocabilary name";
	private static final String BLOAD = "Load";
	private static final String BDELETE = "Delete";
	List<VocabularyT> allVocabularyes;
	IndexedContainer contactContainer;
	private static MyReceiver receiver = new MyReceiver();

	protected static File tempFile;

	private Session session = null;

	public LeftSide(Session session) {
		this.session  = session;
		draw();
		initVocabilaryList();
	}

	private void draw() {
		Label lbUser = new Label("User :");
		lbUser.setContentMode(Label.CONTENT_TEXT);
		lbUser.setValue("User : " + Constants.LOGIN);
		addComponent(lbUser);

		Label lbUserName = new Label("___");
		lbUserName.setContentMode(Label.CONTENT_TEXT);
		addComponent(lbUser);

		addComponent(vocabilaryList);
		setExpandRatio(vocabilaryList, 1);
		vocabilaryList.setSizeFull();

		HorizontalLayout bottomLeftLayout = new HorizontalLayout();
		addComponent(bottomLeftLayout);
		bottomLeftLayout.addComponent(searchField);
		bottomLeftLayout.addComponent(addNewContactButton);
		
		addNewContactButton.addClickListener(new ClickListener() {
			
			@Override
			public void buttonClick(ClickEvent event) {
				String vocabName = searchField.getValue().toString();
				
				// TODO : oбробка ввода ≥ захист в≥д дурн€.
				if (vocabName.equals("") || vocabName == null)
				{
					
				}
				else {
					VocabularyT nVocab = new VocabularyT(vocabName, "my vocabulary", 1);
					DbHelper.addVocabulary(nVocab, session);
					initVocabilaryList();
				}
				
			}
		});

		setSizeFull();

	}

	int selectedId ;
	private void initVocabilaryList() {
		contactContainer = createVocabilaryDatasource();

		vocabilaryList.setContainerDataSource(contactContainer);
		vocabilaryList.setVisibleColumns(new String[] { VNAME, BLOAD, BDELETE });
		vocabilaryList.setSelectable(true);
		vocabilaryList.setImmediate(true);

	
		vocabilaryList.addItemClickListener(new ItemClickListener() {
			@Override
			public void itemClick(ItemClickEvent event) {
				Integer id = (Integer)event.getItemId();
      	      	Integer trueId = allVocabularyes.get(id-1).getId();
				HorizontalSplitPanel splitPanel = UiHelper.setUi(session, trueId);
				
				getUI().setContent(splitPanel);
				getUI().push();
			}
		});
	}

	// TODO : ускладнити регул€рку.
	private static String reqKeyVal = "(.+)[-|=](.+)";
	private static Pattern pattern = Pattern.compile(reqKeyVal);

	public IndexedContainer createVocabilaryDatasource() {
		IndexedContainer ic = new IndexedContainer();

		ic.addContainerProperty(VNAME, String.class, "");
		ic.addContainerProperty(BLOAD, Upload.class, null);
		ic.addContainerProperty(BDELETE, Button.class, null);

		allVocabularyes = DbHelper.getAllVocabularyes(session);

		for (int i = 0; i < allVocabularyes.size(); i++) {

			VocabularyT curentVoc = allVocabularyes.get(i);
			
			Object id = ic.addItem();
			ic.getContainerProperty(id, VNAME).setValue(curentVoc.getVocabName());

			Upload btnLoad = new Upload(null, receiver);
			btnLoad.setData(curentVoc.getId());
			btnLoad.setImmediate(true);
			btnLoad.setButtonCaption("Select file");

			btnLoad.addListener(new Upload.FinishedListener() {
				public void uploadFinished(FinishedEvent event) {
					Integer iid = (Integer) event.getUpload().getData();

					try {
				          FileReader reader = new FileReader(tempFile);
				          BufferedReader bufRead = new BufferedReader(reader);
				          
				          String line;
				          while ((line = bufRead.readLine()) != null) 
				          {
				        	  Matcher matcher = pattern.matcher(line);
				        	  if (matcher.matches()) {
				        	      String key = matcher.group(1);
				        	      String val = matcher.group(2);
				        	      
				        	      Words curent = new Words(key, val, iid);
				        	      DbHelper.addWord(curent, session);
				        	      
//				        	      System.out.println(key + " == " + val  );
				        	  }				  			
				  		  }
				          
				          
				          reader.close();
				          tempFile.delete();
				 
				         
				        } catch (IOException e) {
				          e.printStackTrace();
				        }
				}
			});
			ic.getContainerProperty(id, BLOAD).setValue(btnLoad);

			Button btnDelete = new Button("Delete");
			btnDelete.setData(curentVoc.getId());
			btnDelete.addClickListener(new Button.ClickListener() {
				public void buttonClick(ClickEvent event) {
					Integer iid = (Integer) event.getButton().getData();
					
					System.out.println(Integer.toString(iid)  );

					DbHelper.removeItemByTableName(iid, session, "VocabularyT");
					Notification.show("Vocabulary deleted.");
					
					HorizontalSplitPanel splitPanel = UiHelper.setUi(session, 1);
					
					getUI().setContent(splitPanel);
					getUI().push();
				}
			});
			ic.getContainerProperty(id, BDELETE).setValue(btnDelete);

			
		}
		return ic;

	}

	
	
	public static class MyReceiver implements Receiver {

		private String fileName;
		private String mtype;
		private boolean sleep;
		private int total = 0;

		public OutputStream receiveUpload(String filename, String mimetype) {
	        try {
	            tempFile = File.createTempFile("temp", ".txt");
	            return new FileOutputStream(tempFile);
	          } catch (IOException e) {
	            e.printStackTrace();
	            return null;
	          }
		}

			
		public String getFileName() {
			return fileName;
		}

		public String getMimeType() {
			return mtype;
		}

		public void setSlow(boolean value) {
			sleep = value;
		}

	}
}
