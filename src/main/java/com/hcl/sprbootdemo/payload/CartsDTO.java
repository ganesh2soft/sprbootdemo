package com.hcl.sprbootdemo.payload;

import java.util.ArrayList;
import java.util.List;



public class  CartsDTO{
	private Long cartId;
	private Double totalPrice = 0.0;
	private List<ProductDTO> products = new ArrayList<>();

	public CartsDTO() {
		super();
		
	}

	public CartsDTO(Long cartId, Double totalPrice, List<ProductDTO> products) {
		super();
		this.cartId = cartId;
		this.totalPrice = totalPrice;
		this.products = products;
	}

	public Long getCartId() {
		return cartId;
	}

	public void setCartId(Long cartId) {
		this.cartId = cartId;
	}

	public Double getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(Double totalPrice) {
		this.totalPrice = totalPrice;
	}

	public List<ProductDTO> getProducts() {
		return products;
	}

	public void setProducts(List<ProductDTO> products) {
		this.products = products;
	}

}
