package com.hcl.sprbootdemo.payload;

public class OrderItemDTO {
	private Long orderItemId;
	private ProductDTO productDTO;
	private Integer placedQty;
	private double discount;
	private double orderedProductPrice;

	public OrderItemDTO() {
		super();
		
	}

	public OrderItemDTO(Long orderItemId, ProductDTO productDTO, Integer placedQty, double discount,
			double orderedProductPrice) {
		super();
		this.orderItemId = orderItemId;
		this.productDTO = productDTO;
		this.placedQty= placedQty;
		this.discount = discount;
		this.orderedProductPrice = orderedProductPrice;
	}

	public Long getOrderItemId() {
		return orderItemId;
	}

	public void setOrderItemId(Long orderItemId) {
		this.orderItemId = orderItemId;
	}

	public ProductDTO getProductDTO() {
	    return productDTO;
	}


	public void setProductDTO(ProductDTO productDTO) {
		this.productDTO = productDTO;
	}

	public Integer getPlacedQty() {
		return placedQty;
	}

	public void setPlacedQty(Integer placedQty) {
		this.placedQty = placedQty;
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

}
