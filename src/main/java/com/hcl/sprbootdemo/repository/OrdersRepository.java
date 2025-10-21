package com.hcl.sprbootdemo.repository;

import com.hcl.sprbootdemo.entity.Orders;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrdersRepository extends JpaRepository<Orders, Long> {

	List<Orders> findByEmail(String email);
}
