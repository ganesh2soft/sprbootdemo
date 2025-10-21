package com.hcl.sprbootdemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SpringBootApplication
public class SprbootdemoApplication {
	private static final Logger logger = LoggerFactory.getLogger(SprbootdemoApplication.class);
	public static void main(String[] args) {
		SpringApplication.run(SprbootdemoApplication.class, args);		
		logger.info("####  Spring Boot App Started Sucessfully  ####");
	}
}
