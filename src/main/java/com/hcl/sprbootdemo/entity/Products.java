package com.hcl.sprbootdemo.entity;


import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Table(name="tbl_products")
@Entity
public class Products {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long productId;
	@Enumerated(EnumType.STRING)  // Ensures value is stored as "ELECTRONICS", not 0, 1, 2...
	private Category category;
	private String productName;
	@Column(unique = true)
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
	public int hashCode() {
		return Objects.hash(brandName, category, discount, imageURL, price, productId, productName, quantity,
				specialPrice);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Products other = (Products) obj;
		return Objects.equals(brandName, other.brandName) && category == other.category
				&& Double.doubleToLongBits(discount) == Double.doubleToLongBits(other.discount)
				&& Objects.equals(imageURL, other.imageURL)
				&& Double.doubleToLongBits(price) == Double.doubleToLongBits(other.price)
				&& Objects.equals(productId, other.productId) && Objects.equals(productName, other.productName)
				&& Objects.equals(quantity, other.quantity)
				&& Double.doubleToLongBits(specialPrice) == Double.doubleToLongBits(other.specialPrice);
	}
	@Override
	public String toString() {
		return "Products [productId=" + productId + ", category=" + category + ", productName=" + productName
				+ ", brandName=" + brandName + ", quantity=" + quantity + ", price=" + price + ", discount=" + discount
				+ ", specialPrice=" + specialPrice + ", imageURL=" + imageURL + "]";
	}
	public Products(Long productId, Category category, String productName, String brandName, Integer quantity,
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
	public Products() {
		super();
		
	}
	
}

