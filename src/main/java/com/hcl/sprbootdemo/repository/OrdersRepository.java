package com.hcl.sprbootdemo.repository;

import com.hcl.sprbootdemo.entity.Orders;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrdersRepository extends JpaRepository<Orders, Long> {

//    @Query("SELECT COALESCE(SUM(o.totalAmount), 0) FROM Order o")
//    Double getTotalRevenue();
	
	List<Orders> findByEmail(String email);
}
