package com.hcl.sprbootdemo.security;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.hcl.sprbootdemo.entity.Users;
import com.hcl.sprbootdemo.repository.UsersRepository;

@Component
public class AuthUtil {

    @Autowired
    private UsersRepository usersRepository;

    public String loggedInEmail() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Users user= usersRepository.findUsersByEmail(authentication.getName());
        if (user == null) {
		    throw new UsernameNotFoundException("User Not Found with username: " + authentication.getName());
		}
        
                

        return user.getEmail();
    }

    public Long loggedInUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Users user = usersRepository.findUsersByEmail(authentication.getName());
        		if (user == null) {
        		    throw new UsernameNotFoundException("User Not Found with username: " + authentication.getName());
        		}
        return user.getUserId();
    }

    public Users loggedInUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        Users user = usersRepository.findUsersByEmail(authentication.getName());
        if (user == null) {
		    throw new UsernameNotFoundException("User Not Found with username: " + authentication.getName());
		}
        return user;

    }


}
