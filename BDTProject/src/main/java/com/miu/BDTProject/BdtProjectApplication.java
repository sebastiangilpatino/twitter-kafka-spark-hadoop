package com.miu.BDTProject;

import javax.activation.CommandObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BdtProjectApplication implements CommandLineRunner {

	@Autowired
	private TwitterCaller twitterCaller;
	
	public static void main(String[] args) {
		SpringApplication.run(BdtProjectApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		// TODO Auto-generated method stub
		twitterCaller.connectTwitter();
	}

}
