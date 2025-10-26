package com.hcl.sprbootdemo.payload;


import java.util.ArrayList;
import java.util.List;

public class CartsDTO {
    private Long cartId;
    private Double totalPrice = 0.0;
    private Long userId;
    private List<CartItemDTO> cartItemDTO = new ArrayList<>();  // now CartItemDTO

    // Getters and Setters
    public Long getCartId() { return cartId; }
    public void setCartId(Long cartId) { this.cartId = cartId; }
    public Double getTotalPrice() { return totalPrice; }
    public void setTotalPrice(Double totalPrice) { this.totalPrice = totalPrice; }
    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }
    public List<CartItemDTO> getCartItemDTO() { return cartItemDTO; }
    public void setCartItemDTO(List<CartItemDTO> cartItemDTO) { this.cartItemDTO = cartItemDTO; }

    // Constructors
    public CartsDTO() {}
    public CartsDTO(Long cartId, Double totalPrice, Long userId, List<CartItemDTO> cartItemDTO) {
        this.cartId = cartId;
        this.totalPrice = totalPrice;
        this.userId = userId;
        this.cartItemDTO = cartItemDTO;
    }
}
