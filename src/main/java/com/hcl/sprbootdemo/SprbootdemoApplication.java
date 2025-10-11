package com.hcl.sprbootdemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SprbootdemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(SprbootdemoApplication.class, args);
		System.out.println("Spring Boot App Started Sucessfully....");
	}
	
//	@Bean
//	CommandLineRunner init(UserRepository userRepository) {
//	    return args -> {
//	        Users user = new Users();
//	        user.setId(1L);
//	        user.setName("John Doe");
//	        user.setEmail("john@example.com");
//	        userRepository.save(user);
//	    };
//	}


}
