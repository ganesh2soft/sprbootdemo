package com.hcl.sprbootdemo.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "cart_items")
public class CartItem {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cartItemId;

    @ManyToOne
    @JoinColumn(name = "cart_id")
    private Carts cart;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Products product;

    private Integer quantity;
    private double discount;
    private double productPrice;
	public Long getCartItemId() {
		return cartItemId;
	}
	public void setCartItemId(Long cartItemId) {
		this.cartItemId = cartItemId;
	}
	public Carts getCart() {
		return cart;
	}
	public void setCart(Carts cart) {
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
	/*
	 * This is important. Otherwise cyclic things will happen. Cart calls Cartitems, Cartitem calls Cart
	 * @Override
	   public String toString() {
		return "CartItem [cartItemId=" + cartItemId + ", cart=" + cart + ", product=" + product + ", quantity="
				+ quantity + ", discount=" + discount + ", productPrice=" + productPrice + "]";
	}
	 */
	
	public CartItem() {
		super();
		
	}
	public CartItem(Long cartItemId, Carts cart, Products product, Integer quantity, double discount,
			double productPrice) {
		super();
		this.cartItemId = cartItemId;
		this.cart = cart;
		this.product = product;
		this.quantity = quantity;
		this.discount = discount;
		this.productPrice = productPrice;
	}
    
    
}
