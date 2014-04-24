package com.example.data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="vocabularyes")
public class Vocabularyes {
	
	@Id 
	@GeneratedValue(strategy = GenerationType.AUTO) 
	private int id; 

	@Column(name="name")
	private String vocabName; 

	@Column(name="description")
	private String vocabDescription; 
	
	@Column(name="authorId")
	private int authorId;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getVocabName() {
		return vocabName;
	}

	public void setVocabName(String vocabName) {
		this.vocabName = vocabName;
	}

	public String getVocabDescription() {
		return vocabDescription;
	}

	public void setVocabDescription(String vocabDescription) {
		this.vocabDescription = vocabDescription;
	}

	public int getAuthorId() {
		return authorId;
	}

	public void setAuthorId(int authorId) {
		this.authorId = authorId;
	}

}
   