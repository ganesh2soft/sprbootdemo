package com.hcl.sprbootdemo.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "cart_items")
public class CartItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cartItemId;

    @ManyToOne
    @JoinColumn(name = "cart_id")
    private Carts cart;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Products product;

    @Column(name = "placed_qty")
    private Integer placedQty;  // renamed from quantity

    public CartItem() {
    }

    public CartItem(Long cartItemId, Carts cart, Products product, Integer placedQty) {
        this.cartItemId = cartItemId;
        this.cart = cart;
        this.product = product;
        this.placedQty = placedQty;
    }

    // Getters & Setters
    public Long getCartItemId() { return cartItemId; }
    public void setCartItemId(Long cartItemId) { this.cartItemId = cartItemId; }

    public Carts getCart() { return cart; }
    public void setCart(Carts cart) { this.cart = cart; }

    public Products getProduct() { return product; }
    public void setProduct(Products product) { this.product = product; }

    public Integer getPlacedQty() { return placedQty; }
    public void setPlacedQty(Integer placedQty) { this.placedQty = placedQty; }

	
    
    
}
