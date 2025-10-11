package com.hcl.sprbootdemo.service;

import java.util.List;

import com.hcl.sprbootdemo.entity.Users;

public interface UsersService {
	public Users saveUser(Users user);

	public void deleteUser(Long userId);

	public List<Users> getAllUsers();

	public Users updateUser(Long userId, Users user);


	public Users findUserbyId(Long userId);
	
}
