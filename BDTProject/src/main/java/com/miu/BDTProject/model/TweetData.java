package com.miu.BDTProject.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class TweetData {
	
	public TweetData() {
		super();
	}
	
	public TweetData(String id, String text) {
		super();
		this.id = id;
		this.text = text;
	}
	
	private String id;
	private String text;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}

	@Override
	public String toString() {
		ObjectMapper objectMapper= new ObjectMapper();
		try {
			return objectMapper.writeValueAsString(this);
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "error";
	}

}