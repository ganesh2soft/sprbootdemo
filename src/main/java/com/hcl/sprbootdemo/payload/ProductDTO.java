package com.hcl.sprbootdemo.payload;

import com.hcl.sprbootdemo.entity.Category;

public class ProductDTO {
	private Long productId;
	private Category category;
	private String productName;
	private String brandName;
	private Integer quantity;
	private double price;
	private double discount;
	private double specialPrice;
	
	public ProductDTO() {
		super();
		
	}

	public ProductDTO(Long productId, Category category, String productName, String brandName, Integer quantity, double price,
			double discount, double specialPrice) {
		super();
		this.productId = productId;
		this.category = category;
		this.productName = productName;
		this.brandName = brandName;
		this.quantity = quantity;
		this.price = price;
		this.discount = discount;
		this.specialPrice = specialPrice;
	}

	public Long getproductId() {
		return productId;
	}

	public void setproductId(Long productId) {
		this.productId= productId;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getBrandName() {
		return brandName;
	}

	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public double getDiscount() {
		return discount;
	}

	public void setDiscount(double discount) {
		this.discount = discount;
	}

	public double getSpecialPrice() {
		return specialPrice;
	}

	public void setSpecialPrice(double specialPrice) {
		this.specialPrice = specialPrice;
	}

	@Override
	public String toString() {
		return "Product [productId=" + productId + ", category=" + category + ", productName=" + productName + ", brandName="
				+ brandName + ", quantity=" + quantity + ", price=" + price + ", discount=" + discount
				+ ", specialPrice=" + specialPrice + "]";
	}
}
