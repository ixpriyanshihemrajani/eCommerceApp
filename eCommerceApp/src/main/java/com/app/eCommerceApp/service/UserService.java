package com.app.eCommerceApp.service;

import org.springframework.http.ResponseEntity;

import com.app.eCommerceApp.model.User;

public interface UserService {
	ResponseEntity<?> createUser(User user);
	ResponseEntity<?>updateUser(Long userId, User user);
	ResponseEntity<?> deleteUser(Long userId);
	ResponseEntity<?> getUserById(Long userId);
	ResponseEntity<?> getAllUsers();
	ResponseEntity<?> addProductToCart(Long userId, Long productId);
}