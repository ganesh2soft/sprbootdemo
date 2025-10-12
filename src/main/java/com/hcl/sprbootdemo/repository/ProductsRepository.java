package com.hcl.sprbootdemo.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hcl.sprbootdemo.entity.Products;

@Repository
public interface ProductsRepository extends JpaRepository<Products, Long>{
	public boolean existsByProductNameIgnoreCase(String productName);
	public Optional<Products> findByProductNameContainingIgnoreCase(String keyword);
}
