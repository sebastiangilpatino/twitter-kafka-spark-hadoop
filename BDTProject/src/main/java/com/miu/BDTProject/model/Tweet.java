package com.miu.BDTProject.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Tweet {
	
		
	public Tweet(TweetData data) {
		super();
		this.data = data;
	}

	public Tweet() {
		super();
	}
	
	private TweetData data;

	public TweetData getData() {
		return data;
	}

	public void setData(TweetData data) {
		this.data = data;
	}

	@Override
	public String toString() {
		ObjectMapper objectMapper= new ObjectMapper();
		try {
			return objectMapper.writeValueAsString(this.getData());
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "error";
	}

}
