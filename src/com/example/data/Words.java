package com.example.data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.google.gson.annotations.SerializedName;

@Entity
@Table(name="Words")
public class Words {
	
	@Id 
	@GeneratedValue(strategy = GenerationType.AUTO) 
	@SerializedName("word_id")
	private int id; 

	@Column(name="word")
	@SerializedName("word")
	private String word; 

	@Column(name="value")
	@SerializedName("value")
	private String description; 
	
	@Column(name="vocabulary_id")
	@SerializedName("voc_id")
	private int vocabularyId;

	
	public Words() { }
	
	public Words(String word, String value, int vocabId){
		this.setWord(word);
		this.setDescription(value);
		this.setParentVocabId(vocabId);
	}

	public String getWord() {
		return word;
	}

	public void setWord(String word) {
		this.word = word;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getParentVocabId() {
		return vocabularyId;
	}

	public void setParentVocabId(int vocabularyId) {
		this.vocabularyId = vocabularyId;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
}
