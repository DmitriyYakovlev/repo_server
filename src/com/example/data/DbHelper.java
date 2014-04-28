package com.example.data;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;


public class DbHelper {

	public static Session openSession() {
		Configuration configuration = new Configuration();
		configuration.configure();
		ServiceRegistry serviceRegistry = new ServiceRegistryBuilder()
				.applySettings(configuration.getProperties())
				.buildServiceRegistry();
		SessionFactory sessionFactory = configuration
				.buildSessionFactory(serviceRegistry);
		Session session = sessionFactory.openSession();
		
		return session;
	}

	
	public static void addVocabulary(VocabularyT vocab, Session session) {
        session.beginTransaction();
		session.save(vocab);
		session.getTransaction().commit();
        session.flush();
	}
	
	public static void addWord(Words vocab, Session session) {
        session.beginTransaction();
		session.save(vocab);
		session.getTransaction().commit();
        session.flush();
	}
	
	public static void removeVocabulary(Object rowId, Session session) {
		session.beginTransaction();
		Integer itemId = (Integer) rowId;
		Query query = session.createQuery("DELETE FROM VocabularyT WHERE id=" + itemId);
		query.executeUpdate();
		session.getTransaction().commit();
		session.flush();
	}
	
	public static void removeItemByTableName(Object rowId, Session session, String tableName) {
		session.beginTransaction();
		Integer itemId = (Integer) rowId;
		Query query = session.createQuery("DELETE FROM " + tableName+ " WHERE id=" + itemId);
		query.executeUpdate();
		session.getTransaction().commit();
		session.flush();
	}
	
	public static VocabularyT getVocabularyById(String id, Session session) {
		VocabularyT vocabularyes = null;
		if (id != null) {
			Query query = session.createQuery("FROM VocabularyT WHERE id = " + id);
			vocabularyes = (VocabularyT) query.list().get(0);
		}
		return vocabularyes;
	}

	@SuppressWarnings("unchecked")
	public static List<VocabularyT> getAllVocabularyes(Session session) {
		List<VocabularyT> vocabularyes = null;
		Query query = session.createQuery("FROM VocabularyT ORDER BY id");
		vocabularyes = (List<VocabularyT>) query.list();

		return vocabularyes;
	}
	
	@SuppressWarnings("unchecked")
	public static List<Words> getWordsByVocabularyId (int vocabId, Session session) {
		List<Words> words = null;
		Query query = session.createQuery("FROM Words WHERE vocabulary_id = " + Integer.toString(vocabId));
		words = (List<Words>) query.list();

		return words;
	}

	public static void addVocabularyesTest(Session session) {
		VocabularyT vocabulary = new VocabularyT("first", "desc1", 1);
		VocabularyT vocabulary2 = new VocabularyT("second", "desc1", 1);
		VocabularyT vocabulary3 = new VocabularyT("third", "desc1", 1);

		DbHelper.addVocabulary(vocabulary, session);
		DbHelper.addVocabulary(vocabulary2, session);
		DbHelper.addVocabulary(vocabulary3, session);

	}
}
