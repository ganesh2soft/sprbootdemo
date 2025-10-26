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
	private String imageURL;
	public Long getProductId() {
		return productId;
	}
	public void setProductId(Long productId) {
		this.productId = productId;
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
	public String getImageURL() {
		return imageURL;
	}
	public void setImageURL(String imageURL) {
		this.imageURL = imageURL;
	}
	@Override
	public String toString() {
		return "ProductDTO [productId=" + productId + ", category=" + category + ", productName=" + productName
				+ ", brandName=" + brandName + ", quantity=" + quantity + ", price=" + price + ", discount=" + discount
				+ ", specialPrice=" + specialPrice + ", imageURL=" + imageURL + "]";
	}
	public ProductDTO(Long productId, Category category, String productName, String brandName, Integer quantity,
			double price, double discount, double specialPrice, String imageURL) {
		super();
		this.productId = productId;
		this.category = category;
		this.productName = productName;
		this.brandName = brandName;
		this.quantity = quantity;
		this.price = price;
		this.discount = discount;
		this.specialPrice = specialPrice;
		this.imageURL = imageURL;
	}
	public ProductDTO() {
		super();
		
	}
	
	
}
