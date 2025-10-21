package com.hcl.sprbootdemo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.hcl.sprbootdemo.entity.Users;
import com.hcl.sprbootdemo.exception.ResourceNotFoundException;
import com.hcl.sprbootdemo.repository.UsersRepository;

@Service
public class UsersServiceImpl implements UsersService {

	
	@Autowired
	UsersRepository userRepo;
	@Autowired
    private PasswordEncoder pwdEncoder;
	
	@Override
	public Users saveUser(Users user) {
		
		return userRepo.save(user);
	}
    
	@Override
	public Users findUserbyId(Long userId) {
		return userRepo.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "userId", userId));
	}
	
	@Override
	public void deleteUser(Long userId) {
		 userRepo.deleteById(userId);
		
	}

	@Override
	public List<Users> getAllUsers() {
		
		return userRepo.findAll();
	}

	@Override
	public Users updateUser(Long userId, Users userDetails) {
        Users user = userRepo.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "userId", userId));

        if (userDetails.getPassword() != null && !userDetails.getPassword().isBlank()) {
            user.setPassword(pwdEncoder.encode(userDetails.getPassword()));
        }
   
        user.setAddress(userDetails.getAddress());
        return userRepo.save(user);
    }
	
	

}
