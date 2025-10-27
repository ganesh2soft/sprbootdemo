package com.hcl.sprbootdemo.service;

import java.util.List;

import com.hcl.sprbootdemo.payload.CartsDTO;

import jakarta.transaction.Transactional;

public interface CartsService {    

    List<CartsDTO> getAllCarts();
    void addToCart(String userEmail, Long productId, int quantity);    
    CartsDTO getCart(String email);  
    
    
    @Transactional
    CartsDTO updateProductQuantityInCart(Long productId, Integer quantity);
    void deleteProductsFromUserCart(String email, List<Long> productIds);   
  
        
}
