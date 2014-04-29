package com.example.ui;


import org.hibernate.Session;

import com.vaadin.ui.HorizontalSplitPanel;

public class UiHelper {

	public static HorizontalSplitPanel getUiHorizontalSplitPanel(Session session, Integer firstVocabId) {
		
		HorizontalSplitPanel splitPanel = new HorizontalSplitPanel();

		LeftContent leftLayout = new LeftContent(session);	
		splitPanel.addComponent(leftLayout);
		
		RightContent rightLayout = new RightContent(session, firstVocabId);
		splitPanel.addComponent(rightLayout);

		return splitPanel;
	}
	
}

