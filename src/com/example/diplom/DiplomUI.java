package com.example.diplom;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

import javax.servlet.annotation.WebServlet;


import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;

import com.example.data.Vocabularyes;
import com.example.ui.AutorizationView;
import com.example.ui.LeftSide;
import com.example.ui.RightSide;
import com.sun.xml.internal.fastinfoset.vocab.Vocabulary;
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
import com.vaadin.ui.Upload;
import com.vaadin.ui.Upload.FinishedEvent;
import com.vaadin.ui.Upload.Receiver;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.Reindeer;

@SuppressWarnings("serial")
@Theme("diplom")
public class DiplomUI extends UI {

	@WebServlet(value = "/*", asyncSupported = true)
	@VaadinServletConfiguration(productionMode = false, ui = DiplomUI.class)
	public static class Servlet extends VaadinServlet {
	}

	private Session session = null;

	@Override
	protected void init(VaadinRequest request) {

		openSession();

		AutorizationView autorization = new AutorizationView();
		setContent(autorization);

		// initLayout();

		List<Vocabularyes> vocabularyes = getAllVocabularyes();

		int k = 0;
		k++;
	}

	private void openSession() {
		Configuration configuration = new Configuration();
		configuration.configure();
		ServiceRegistry serviceRegistry = new ServiceRegistryBuilder()
				.applySettings(configuration.getProperties())
				.buildServiceRegistry();
		SessionFactory sessionFactory = configuration
				.buildSessionFactory(serviceRegistry);
		session = sessionFactory.openSession();
	}

	public void saveVocabulary(Vocabularyes vocabularyes) {
		session.beginTransaction();
		session.save(vocabularyes);
		session.getTransaction().commit();
		session.flush();
	}

	public void getVocabularyById(String id) {
		Vocabularyes vocabularyes = null;
		if (id != null) {
			Query query = session.createQuery("FROM Person WHERE id = " + id);
			vocabularyes = (Vocabularyes) query.list().get(0);
		}
	}

	public List<Vocabularyes> getAllVocabularyes() {
		List<Vocabularyes> vocabularyes = null;
		Query query = session.createQuery("FROM Person");
		vocabularyes = (List<Vocabularyes>) query.list();

		return vocabularyes;
	}

	private void initLayout() {

		HorizontalSplitPanel splitPanel = new HorizontalSplitPanel();
		setContent(splitPanel);

		LeftSide leftLayout = new LeftSide();
		RightSide rightLayout = new RightSide();

		splitPanel.addComponent(leftLayout);
		splitPanel.addComponent(rightLayout);

	}

}