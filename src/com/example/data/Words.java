package com.example.data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import antlr.collections.List;


@Entity
@Table(name="vocabularyes")
public class Words {
	
	@Id 
	@GeneratedValue(strategy = GenerationType.AUTO) 
	private int id; 

	@Column(name="word")
	private String word; 

	@Column(name="value")
	private String vocabDescription; 
	
//	@Column(name="vocabylaryes")
//	private List<Integer> vocabylaryes;

}
