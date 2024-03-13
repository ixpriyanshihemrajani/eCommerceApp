package com.app.eCommerceApp.service;

import org.springframework.http.ResponseEntity;

import com.app.eCommerceApp.model.UserInfo;


public interface UserService {
	ResponseEntity<?> createUser(UserInfo user);
	ResponseEntity<?> updateUser(Long userId, UserInfo user);
	ResponseEntity<?> deleteUser(Long userId);
	ResponseEntity<?> getUserById(Long userId);
	ResponseEntity<?> getAllUsers();
	ResponseEntity<?> addProductToCart(Long userId, Long productId);
}