package com.hcl.sprbootdemo.service;

import java.util.List;

import com.hcl.sprbootdemo.payload.CartsDTO;

import jakarta.transaction.Transactional;

public interface CartsService {
    CartsDTO addProductsToCart(Long productId, Integer quantity);

    List<CartsDTO> getAllCarts();

    // CartDTO getCart(String emailId, Long cartId);
    CartsDTO getCart(String email);

    @Transactional
    CartsDTO updateProductQuantityInCart(Long productId, Integer quantity);

    String deleteProductFromCart(Long cartId, Long productId);

    void updateProductInCarts(Long cartId, Long productId);

    void addToCart(String userEmail, Long productId, int quantity);

    // New method to delete multiple products by user email
    void deleteProductsFromUserCart(String email, List<Long> productIds);
}
