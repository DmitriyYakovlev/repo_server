package com.example.data;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

@Entity
@Table(name = "vocabularyes")
public class VocabularyT implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@SerializedName("voc_id")
	private int id;

	@Column(name = "name")
	@SerializedName("voc_name")
	private String vocabName;

	@Column(name = "description")
	@SerializedName("voc_desc")
	private String vocabDescription;

	@Column(name = "authorId")
	@SerializedName("voc_autor_id")
	private int authorId;

	public VocabularyT() {
	}

	public VocabularyT(String name, String desc, int authorId) {
		this.vocabName = name;
		this.vocabDescription = desc;
		this.authorId = authorId;
	}

	public String toJson() {
		return new Gson().toJson(this);
	}

	public static VocabularyT fromJson(String jsonString) {
		VocabularyT devInfo = new Gson()
				.fromJson(jsonString, VocabularyT.class);
		return devInfo;
	}

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
