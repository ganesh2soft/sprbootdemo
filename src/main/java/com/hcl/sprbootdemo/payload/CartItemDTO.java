package com.hcl.sprbootdemo.payload;

import com.hcl.sprbootdemo.entity.Products;

public class CartItemDTO {

	private Long cartItemId;
	private CartsDTO cart;
	private Products product;
	private Integer quantity;
	private double discount;
	private double productPrice;

	public CartItemDTO() {
		super();
	
	}

	public CartItemDTO(Long cartItemId, CartsDTO cart, Products product, Integer quantity, double discount,
			double productPrice) {
		super();
		this.cartItemId = cartItemId;
		this.cart = cart;
		this.product = product;
		this.quantity = quantity;
		this.discount = discount;
		this.productPrice = productPrice;
	}

	public Long getCartItemId() {
		return cartItemId;
	}

	public void setCartItemId(Long cartItemId) {
		this.cartItemId = cartItemId;
	}

	public CartsDTO getCart() {
		return cart;
	}

	public void setCart(CartsDTO cart) {
		this.cart = cart;
	}

	public Products getProduct() {
		return product;
	}

	public void setProduct(Products product) {
		this.product = product;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public double getDiscount() {
		return discount;
	}

	public void setDiscount(double discount) {
		this.discount = discount;
	}

	public double getProductPrice() {
		return productPrice;
	}

	public void setProductPrice(double productPrice) {
		this.productPrice = productPrice;
	}

}
