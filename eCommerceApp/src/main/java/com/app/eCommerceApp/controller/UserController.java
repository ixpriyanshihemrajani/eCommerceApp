package com.app.eCommerceApp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import com.app.eCommerceApp.model.AuthRequest;
import com.app.eCommerceApp.model.UserInfo;
import com.app.eCommerceApp.service.JwtService;
import com.app.eCommerceApp.service.UserService;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;
    
    @Autowired
    private JwtService jwtService;
    
    @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping("/create")
    public ResponseEntity<?> createUser(@RequestBody UserInfo user) {
        return userService.createUser(user);
    }

    @PutMapping("/update/{userId}")
    public ResponseEntity<?> updateUser(@PathVariable Long userId, @RequestBody UserInfo user) {
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
    	System.out.println("/user/get all");
        return userService.getAllUsers();
    }
    
    @PostMapping("/{userId}/addProductToCart")
    public ResponseEntity<?> addProductToCart(
            @RequestParam("productId") Long productId,
            @PathVariable("userId") Long userId) {
        return userService.addProductToCart(userId, productId);
    }
    
    
    //JWT TOKEN WORK 
    
    
    @GetMapping("/welcome") 
    public String welcome() { 
        return "Welcome this endpoint is not secure"; 
    } 
    
    @GetMapping("/user/userProfile") 
    @PreAuthorize("hasAuthority('ROLE_USER')") 
    public String userProfile() { 
        return "Welcome to User Profile"; 
    } 
    
    @GetMapping("/admin/adminProfile") 
    @PreAuthorize("hasAuthority('ROLE_ADMIN')") 
    public String adminProfile() { 
        return "Welcome to Admin Profile"; 
    } 
    
    @PostMapping("/generateToken") 
    public String authenticateAndGetToken(@RequestBody AuthRequest authRequest) { 
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword())); 
        if (authentication.isAuthenticated()) { 
            return jwtService.generateToken(authRequest.getUsername()); 
        } else { 
            throw new UsernameNotFoundException("invalid user request !"); 
        } 
    } 
}