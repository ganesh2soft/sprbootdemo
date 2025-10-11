package com.hcl.sprbootdemo.payload;

public class CartItemDTO {
	private Long productId;
	private Integer quantity;

	public CartItemDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public CartItemDTO(Long productId, Integer quantity) {
		super();
		this.productId = productId;
		this.quantity = quantity;
	}

	public Long getProductId() {
		return productId;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

}
