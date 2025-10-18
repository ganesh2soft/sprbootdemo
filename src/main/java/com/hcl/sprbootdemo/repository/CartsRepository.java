package com.hcl.sprbootdemo.repository;

import com.hcl.sprbootdemo.entity.Carts;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CartsRepository extends JpaRepository<Carts, Long> {
	
    @Query("SELECT c FROM Carts c WHERE c.user.email = ?1")
    Carts findCartByEmail(String email);

    @Query("SELECT c FROM Carts c WHERE c.user.email = ?1 AND c.id = ?2")
    Carts findCartByEmailAndCartId(String emailId, Long cartId);

    @Query("SELECT c FROM Carts c JOIN FETCH c.cartItems ci JOIN FETCH ci.product p WHERE p.id = ?1")
    List<Carts> findCartsByProductId(Long productId);

    @Query("SELECT c FROM Carts c WHERE c.user.email = :email")
    Optional<Carts> findByUserEmail(@Param("email") String email);
	}

