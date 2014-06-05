package com.example.data;

import java.io.Serializable;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

public class WordsListJH implements Serializable {

	private static final long serialVersionUID = 7387701931424294715L;

	@SerializedName("result")
	private List<Words> result;

	@SerializedName("success")
	private String success;

	public String toJson() {
		return new Gson().toJson(this);
	}
	
	public WordsListJH(List<Words> result, String success) {
		this.result = result;
		this.success = success;
	}

	public static WordsListJH fromJson(String jsonString) {
		WordsListJH devInfo = new Gson().fromJson(jsonString,
				WordsListJH.class);
		return devInfo;
	}

}
