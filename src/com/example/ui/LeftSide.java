package com.example.ui;

import java.io.IOException;
import java.io.OutputStream;

import com.vaadin.data.Property;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.util.IndexedContainer;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
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
	IndexedContainer contactContainer = createVocabilaryDatasource();

	private static MyReceiver receiver = new MyReceiver();

	public LeftSide() {
		draw();
		initVocabilaryList();
	}

	private void draw() {

		Label lbUser = new Label("User :");
		lbUser.setContentMode(Label.CONTENT_TEXT);
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

		setSizeFull();

	}

	private void initVocabilaryList() {
		vocabilaryList.setContainerDataSource(contactContainer);
		vocabilaryList
				.setVisibleColumns(new String[] { VNAME, BLOAD, BDELETE });
		vocabilaryList.setSelectable(true);
		vocabilaryList.setImmediate(true);

		vocabilaryList
				.addValueChangeListener(new Property.ValueChangeListener() {

					@Override
					public void valueChange(ValueChangeEvent event) {
						Object contactId = vocabilaryList.getValue();
					}
				});
	}

	private static IndexedContainer createVocabilaryDatasource() {
		IndexedContainer ic = new IndexedContainer();

		ic.addContainerProperty(VNAME, String.class, "");
		ic.addContainerProperty(BLOAD, Upload.class, null);
		ic.addContainerProperty(BDELETE, Button.class, null);

		String[] fnames = { "Peter", "Alice", "Joshua", "Mike", "Olivia",
				"Nina", "Alex", "Rita", "Dan", "Umberto", "Henrik", "Rene",
				"Lisa", "Marge" };

		for (int i = 0; i < 1000; i++) {
			Object id = ic.addItem();
			ic.getContainerProperty(id, VNAME).setValue(fnames[(int) (fnames.length * Math.random())]);

			Integer itemId = new Integer(i);

			Upload btnLoad = new Upload(null, receiver);
			btnLoad.setData(itemId);
			btnLoad.setImmediate(true);
			 btnLoad.setButtonCaption("Select file");

			btnLoad.addListener(new Upload.FinishedListener() {
			public void uploadFinished(FinishedEvent event) {
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


	public static class MyReceiver implements Receiver {

		private String fileName;
		private String mtype;
		private boolean sleep;
		private int total = 0;

		public OutputStream receiveUpload(String filename, String mimetype) {
			fileName = filename;
			mtype = mimetype;
			return new OutputStream() {
				@Override
				public void write(int b) throws IOException {
					total++;
					if (sleep && total % 10000 == 0) {
						try {
							Thread.sleep(100);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
				}
			};
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
