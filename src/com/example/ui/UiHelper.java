package com.example.ui;

import java.util.List;

import org.hibernate.Session;

import com.example.data.DbHelper;
import com.example.data.VocabularyT;
import com.vaadin.ui.HorizontalSplitPanel;

public class UiHelper {

	public static HorizontalSplitPanel setUi(Session session, Integer firstVocabId) {
		
		HorizontalSplitPanel splitPanel = new HorizontalSplitPanel();

		LeftSide leftLayout = new LeftSide(session);	
		RightSide rightLayout = new RightSide(session, firstVocabId);
		
		splitPanel.addComponent(leftLayout);
		splitPanel.addComponent(rightLayout);
		
		return splitPanel;
	}
	
}
