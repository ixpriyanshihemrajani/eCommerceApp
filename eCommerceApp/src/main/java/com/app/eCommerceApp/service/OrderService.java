package com.app.eCommerceApp.service;

import org.springframework.http.ResponseEntity;

public interface OrderService {
    ResponseEntity<?> orderProductsInCart(Long userId);
    ResponseEntity<?> getAllOrders();
	ResponseEntity<?> getOrderedProducts(Long userId);
}