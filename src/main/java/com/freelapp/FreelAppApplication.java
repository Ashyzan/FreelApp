package com.freelapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync  
public class FreelAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(FreelAppApplication.class, args);
		
		
	}

}
