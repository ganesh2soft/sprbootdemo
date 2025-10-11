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

    private Integer quantity;
    private double discount;
    private double productPrice;
}
