package com.hcl.sprbootdemo.security;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.hcl.sprbootdemo.entity.Users;
import com.hcl.sprbootdemo.repository.UsersRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class UserAuthConfig implements AuthenticationProvider {

	private static final Logger logger = LoggerFactory.getLogger(UserAuthConfig.class);
	@Autowired
	UsersRepository usersRepository;
	
	@Autowired
	PasswordEncoder pwdEncoder;
	
	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		/*
		 * Below 2 lines are getting User inputs at login page Authentication auth =
		 * SecurityContextHolder.getContext().getAuthentication(); String username =
		 * auth.getName(); Object credentials = auth.getCredentials();
		 */
		String username = authentication.getName();
		/*
		 * Object credentials = authentication.getCredentials().toString();
		 */
		String pwd = authentication.getCredentials().toString();
		logger.info("Username's Email ID refered as username :{}" + username);
	
		logger.info("Fetching from DB {}" , usersRepository.findUsersByEmail(username));
		Users usersObj = usersRepository.findUsersByEmail(username);
	
		logger.info("User Object is {}", usersObj);
		if (usersObj == null) {
			throw new UsernameNotFoundException("User not found: " + username);
		} else if (pwdEncoder.matches(pwd, usersObj.getPassword())) {
			logger.info(" As password matched, User obj {} is", usersObj);
			List<GrantedAuthority> authorities = new ArrayList<>();
			authorities.add(new SimpleGrantedAuthority(usersObj.getRoles()));
			return new UsernamePasswordAuthenticationToken(username, pwd, authorities);

		} else {
			
			logger.info("Bad Credentials Alert");
			throw new BadCredentialsException("Invalid Password");
		}
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
	}
}