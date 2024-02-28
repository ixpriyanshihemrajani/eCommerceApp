package com.app.eCommerceApp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.app.eCommerceApp.model.User;
import com.app.eCommerceApp.service.UserService;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/create")
    public ResponseEntity<?> createUser(@RequestBody User user) {
        return userService.createUser(user);
    }

    @PutMapping("/update/{userId}")
    public ResponseEntity<?> updateUser(@PathVariable Long userId, @RequestBody User user) {
        return userService.updateUser(userId, user);
    }

    @DeleteMapping("/delete/{userId}")
    public ResponseEntity<?> deleteUser(@PathVariable Long userId) {
        return userService.deleteUser(userId);
    }

    @GetMapping("/getById/{userId}")
    public ResponseEntity<?> getUserById(@PathVariable Long userId) {
        return userService.getUserById(userId);
    }

    @GetMapping("/getAll")
    public ResponseEntity<?> getAllUsers() {
        return userService.getAllUsers();
    }
    
    @PostMapping("/{userId}/addProductToCart")
    public ResponseEntity<?> addProductToCart(
            @RequestParam("productId") Long productId,
            @PathVariable("userId") Long userId) {
        return userService.addProductToCart(userId, productId);
    }
}