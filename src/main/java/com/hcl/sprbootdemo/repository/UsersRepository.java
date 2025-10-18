package com.hcl.sprbootdemo.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import com.hcl.sprbootdemo.entity.Users;

@Repository
public interface UsersRepository extends JpaRepository<Users, Long> {

	public Users findUsersByEmail(String email);

	  Optional<Users> findByEmail(String email);
}
