package com.hcl.sprbootdemo.payload;

public class CartItemDTO {
    private Long cartItemId;
    private ProductDTO product;   // the product info
    private Integer placedQty;    // quantity added to cart
       // any discount applied
	public Long getCartItemId() {
		return cartItemId;
	}
	public void setCartItemId(Long cartItemId) {
		this.cartItemId = cartItemId;
	}
	public ProductDTO getProduct() {
		return product;
	}
	public void setProduct(ProductDTO product) {
		this.product = product;
	}
	public Integer getPlacedQty() {
		return placedQty;
	}
	public void setPlacedQty(Integer placedQty) {
		this.placedQty = placedQty;
	}
	public CartItemDTO(Long cartItemId, ProductDTO product, Integer placedQty) {
		super();
		this.cartItemId = cartItemId;
		this.product = product;
		this.placedQty = placedQty;
	}
	public CartItemDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

    // Getters and Setters
    
}
