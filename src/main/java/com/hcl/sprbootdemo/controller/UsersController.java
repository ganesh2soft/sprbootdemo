package com.hcl.sprbootdemo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hcl.sprbootdemo.entity.Users;
import com.hcl.sprbootdemo.repository.UsersRepository;
import com.hcl.sprbootdemo.security.SecurityConstants;
import com.hcl.sprbootdemo.service.UsersService;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@RestController
@RequestMapping("/api/users")
public class UsersController {
	private static final Logger logger = LoggerFactory.getLogger(UsersController.class);

	@Autowired
	private UsersService usersService;

	@Autowired
	private UsersRepository usersRepo;
	@Autowired
	PasswordEncoder pwdEncoder;
	@Autowired
	HttpServletResponse httpServletResponse;
	

	@GetMapping("/hello")
	public String helloFn() {
		return "User Management Controller response!";
	}
	
	@PostMapping("/add")
	public Users createUser(@RequestBody Users user) {
		user.setPassword(pwdEncoder.encode(user.getPassword()));
		return usersService.saveUser(user);
	}
	
	@GetMapping("/get/{userId}")
	public Users getUser(@PathVariable Long userId) {
		return usersService.findUserbyId(userId);

	}
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@GetMapping("/get/all")
	public List<Users> getAllUsers() {
		return usersService.getAllUsers();
	}
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@DeleteMapping("/delete/{user_id}")
	public String deleteUser(@PathVariable Long userId) {
		usersService.deleteUser(userId);
		return "User deleted successfully!";
	}

	 @PutMapping("/update/{userId}")
	    public ResponseEntity<Users> updateUser(
	            @PathVariable Long userId,
	            @Valid @RequestBody Users user) {
	        
	        Users updatedUser = usersService.updateUser(userId, user);
	        
	        return ResponseEntity.ok(updatedUser);
	    }
	

	@GetMapping("/login")
	public ResponseEntity<Users> processLoginRequest() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		Users usersObj = usersRepo.findUsersByEmail(auth.getName());
		logger.info("Response body user details=========" + usersObj);
		try {
			
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
		}
				
		String hr=httpServletResponse.getHeader(SecurityConstants.JWT_HEADER);		
		System.out.println("hr"+hr);		
		HttpHeaders headers = new HttpHeaders();			
		ResponseEntity<Users> responseEntity = new ResponseEntity<>(usersObj,headers,HttpStatus.OK);		
		logger.info("Return result to React front end as below"+responseEntity);
		
		return responseEntity;
		}
	}
