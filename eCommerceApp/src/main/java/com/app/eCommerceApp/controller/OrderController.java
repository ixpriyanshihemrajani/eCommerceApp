package com.app.eCommerceApp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.eCommerceApp.service.OrderService;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping("/placeOrder/{userId}")
    public ResponseEntity<?> placeOrder(@PathVariable("userId") Long userId) {
        return orderService.orderProductsInCart(userId);
    }
    
    @GetMapping("/getAll")
	public ResponseEntity<?> getAllOrders(){
		return orderService.getAllOrders();
	}
    
    @GetMapping("/{userId}/products")
    public ResponseEntity<?> getOrderedProducts(@PathVariable Long userId) {
        return orderService.getOrderedProducts(userId);
    }
}
