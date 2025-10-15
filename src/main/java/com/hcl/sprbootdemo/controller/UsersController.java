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

@RestController
@RequestMapping("/api/users")
public class UsersController {

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
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@GetMapping("/get/{id}")
	public Users getUser(@PathVariable Long id) {
		return usersService.findUserbyId(id);

	}
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@GetMapping("/get/all")
	public List<Users> getAllUsers() {
		return usersService.getAllUsers();
	}
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@DeleteMapping("/delete/{id}")
	public String deleteUser(@PathVariable Long userId) {
		usersService.deleteUser(userId);
		return "User deleted successfully!";
	}

	@PutMapping("/update/{id}")
	public Users updateUser(@PathVariable Long userId, @RequestBody Users user) {
		return usersService.updateUser(userId, user);
	}

	

	@GetMapping("/login")
	public ResponseEntity<Users> processLoginRequest() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		Users usersObj = usersRepo.findUsersByEmail(auth.getName());
		System.out.println("Response body user details=========" + usersObj);
		try {
			// Introduce a 3-second (3000 milliseconds) delay
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
		}
		
		
		String hr=httpServletResponse.getHeader(SecurityConstants.JWT_HEADER);
		
		System.out.println("hr"+hr);
		
		HttpHeaders headers = new HttpHeaders();
		// headers.add(SecurityConstants.JWT_HEADER, hr);
		
		ResponseEntity<Users> responseEntity = new ResponseEntity<>(usersObj,headers,HttpStatus.OK);
		
		System.out.println("Return result to React front end as below");
		System.out.println(responseEntity);
		return responseEntity;
	
	}
	
	/*
	 * @GetMapping("/login") public ResponseEntity<Users>
	 * loginFn(@RequestParam(value = "username", required = false) String username,
	 * 
	 * @RequestParam(value = "password", required = false) String password) {
	 * 
	 * Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	 * 
	 * Users usersObj = usersRepo.findUsersByEmail(auth.getName());
	 * System.out.println("Welcomed user is  " + usersObj);
	 * 
	 * return ResponseEntity.ok(usersObj);
	 * 
	 * }
	 */

}
