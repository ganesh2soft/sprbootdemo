package com.hcl.sprbootdemo.service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.hcl.sprbootdemo.entity.Products;


public interface ProductsService {
	public Products saveProduct(Products product);

    public void deleteProduct(Long productId);

    public List<Products> getAllProducts();

    public Products updateProduct(Long productId, Products product);

    public Products findProductById(Long productId);

//	public ProductDTO searchProductByKeyword(String keyword);
	

}

