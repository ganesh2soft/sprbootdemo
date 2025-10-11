package com.hcl.sprbootdemo.repository;

import com.hcl.sprbootdemo.entity.Carts;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartsRepository extends JpaRepository<Carts, Long> {
	/*
    @Query("SELECT c FROM Cart c WHERE c.users.email = ?1")
    Carts findCartByEmail(String email);

    @Query("SELECT c FROM Cart c WHERE c.users.email = ?1 AND c.id = ?2")
    Carts findCartByEmailAndCartId(String emailId, Long cartId);

    @Query("SELECT c FROM Cart c JOIN FETCH c.cartItems ci JOIN FETCH ci.product p WHERE p.id = ?1")
    List<Carts> findCartsByProductId(Long productId);

*/
	}

