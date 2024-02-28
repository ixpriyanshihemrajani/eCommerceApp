package com.app.eCommerceApp.repository;

import com.app.eCommerceApp.model.Cart;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CartRepository extends JpaRepository<Cart, Long> {
	@Query("SELECT COUNT(c) > 0 FROM Cart c JOIN c.products p WHERE p.id = ?1")
    boolean existsByProductsId(Long productId);
	
	Optional<Cart> findByUserId(Long userId);

    
}