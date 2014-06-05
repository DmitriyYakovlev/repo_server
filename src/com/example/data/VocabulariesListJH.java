package com.example.data;

import java.io.Serializable;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

public class VocabulariesListJH implements Serializable {

	private static final long serialVersionUID = 8663063833047454359L;

	@SerializedName("result")
	private List<VocabularyT> result;

	@SerializedName("success")
	private String success;

	public String toJson() {
		return new Gson().toJson(this);
	}
	
	public VocabulariesListJH(List<VocabularyT> result, String success) {
		this.result = result;
		this.success = success;
	}

	public static VocabulariesListJH fromJson(String jsonString) {
		VocabulariesListJH devInfo = new Gson().fromJson(jsonString,
				VocabulariesListJH.class);
		return devInfo;
	}

}
