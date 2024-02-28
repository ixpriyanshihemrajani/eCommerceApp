package com.app.eCommerceApp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.eCommerceApp.service.CartService;

@RestController
@RequestMapping("/api/carts")
public class CartController {
	
	@Autowired
	private CartService cartService;
	
	@GetMapping("/getAll")
	public ResponseEntity<?> getAllCarts(){
		return cartService.getAllCarts();
	}

	
}
