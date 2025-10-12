package com.hcl.sprbootdemo.service;

import java.util.List;

import com.hcl.sprbootdemo.entity.Carts;
import com.hcl.sprbootdemo.payload.CartsDTO;

public interface CartsService {
	public Carts createCart(Carts cart);

	public CartsDTO addProductToCart(Long productId, Integer quantity);

	public List<CartsDTO> getAllCarts();

	public CartsDTO getCart(String emailId, Long cartId);

	public CartsDTO updateProductQuantityInCart(Long cartId, Long productId, Integer quantity);

	public void updateProductInCarts(Long cartId, Long productId);

	public String deleteProductFromCart(Long cartId, Long productId);
}
