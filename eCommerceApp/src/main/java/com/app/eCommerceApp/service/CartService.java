package com.app.eCommerceApp.service;

import org.springframework.http.ResponseEntity;

import com.app.eCommerceApp.model.Cart;

public interface CartService {
	
	ResponseEntity<?> createCart(Cart cart);
	ResponseEntity<?> updateCart(Long cartId, Cart cart);
	ResponseEntity<?> deleteCart(Long cartId);
	ResponseEntity<?> getCartById(Long cartId);
	ResponseEntity<?> getAllCarts();
	
	

}

