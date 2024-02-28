package com.app.eCommerceApp.service;

import org.springframework.http.ResponseEntity;

import com.app.eCommerceApp.model.Product;

public interface ProductService {
	ResponseEntity<?> createProduct(Product product);
	ResponseEntity<?>updateProduct(Long productId, Product product);
	ResponseEntity<?> deleteProduct(Long productId);
	ResponseEntity<?> getProductById(Long productId);
	ResponseEntity<?> getAllProducts();

}
