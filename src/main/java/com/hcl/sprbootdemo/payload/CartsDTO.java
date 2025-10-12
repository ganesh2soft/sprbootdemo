package com.hcl.sprbootdemo.payload;

import java.util.ArrayList;
import java.util.List;

import com.hcl.sprbootdemo.entity.Products;

public class CartsDTO {
	private Long cartId;
	private Double totalPrice = 0.0;
	private List<Products> products = new ArrayList<>();

	public CartsDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public CartsDTO(Long cartId, Double totalPrice, List<Products> products) {
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

	public List<Products> getProducts() {
		return products;
	}

	public void setProducts(List<Products> products) {
		this.products = products;
	}

}
