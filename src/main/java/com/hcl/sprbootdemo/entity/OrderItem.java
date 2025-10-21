package com.hcl.sprbootdemo.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "order_items")

public class OrderItem {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long orderItemId;

	@ManyToOne
	@JoinColumn(name = "product_id")
	private Products product;

	@ManyToOne
	@JoinColumn(name = "order_id")
	private Orders order;

	private Integer quantity;
	private double discount;
	private double orderedProductPrice;

	public Long getOrderItemId() {
		return orderItemId;
	}

	public void setOrderItemId(Long orderItemId) {
		this.orderItemId = orderItemId;
	}

	public Products getProduct() {
		return product;
	}

	public void setProduct(Products product) {
		this.product = product;
	}

	public Orders getOrder() {
		return order;
	}

	public void setOrder(Orders order) {
		this.order = order;
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

	public double getOrderedProductPrice() {
		return orderedProductPrice;
	}

	public void setOrderedProductPrice(double orderedProductPrice) {
		this.orderedProductPrice = orderedProductPrice;
	}

	@Override
	public String toString() {
		return "OrderItem [orderItemId=" + orderItemId + ", product=" + product + ", order=" + order + ", quantity="
				+ quantity + ", discount=" + discount + ", orderedProductPrice=" + orderedProductPrice + "]";
	}

	public OrderItem(Long orderItemId, Products product, Orders order, Integer quantity, double discount,
			double orderedProductPrice) {
		super();
		this.orderItemId = orderItemId;
		this.product = product;
		this.order = order;
		this.quantity = quantity;
		this.discount = discount;
		this.orderedProductPrice = orderedProductPrice;
	}

	public OrderItem() {
		super();
	}
}