package com.app.eCommerceApp.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.app.eCommerceApp.model.Cart;
import com.app.eCommerceApp.model.Product;
import com.app.eCommerceApp.model.User;
import com.app.eCommerceApp.repository.UserRepository;
import com.app.eCommerceApp.repository.CartRepository;
import com.app.eCommerceApp.repository.ProductRepository;
import com.app.eCommerceApp.response.ResponseMessage;


@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private CartRepository cartRepository;
    
    @Autowired
    private ProductRepository productRepository;
    
    @Override
    public ResponseEntity<?> createUser(User user) {
        try {
            Optional<User> existingUser = userRepository.findByEmail(user.getEmail());
            if (existingUser.isPresent()) {
                return new ResponseEntity<>(new ResponseMessage("User with email " + user.getEmail() + " already exists", HttpStatus.BAD_REQUEST.value()), HttpStatus.BAD_REQUEST);
            }

            if (user.getCart() == null) {
                Cart cart = new Cart();
                Cart savedCart = cartRepository.save(cart);
                user.setCart(savedCart);
                cart.setUser(user);
            }

            User createdUser = userRepository.save(user);
            return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(new ResponseMessage("Error creating user: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }



    @Override
    public ResponseEntity<?> updateUser(Long userId, User user) {
        try {
            Optional<User> optionalUser = userRepository.findById(userId);
            if (optionalUser.isPresent()) {
                user.setId(userId);
                userRepository.save(user);
                return new ResponseEntity<>(new ResponseMessage("User updated successfully",HttpStatus.OK.value()), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(new ResponseMessage("User not found",HttpStatus.NOT_FOUND.value()), HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(new ResponseMessage("Error updating user: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<?> deleteUser(Long userId) {
        try {
            if (userRepository.existsById(userId)) {
                userRepository.deleteById(userId);
                return new ResponseEntity<>(new ResponseMessage("User deleted successfully",HttpStatus.OK.value()), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(new ResponseMessage("User not found",HttpStatus.NOT_FOUND.value()), HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(new ResponseMessage("Error deleting user: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<?> getUserById(Long userId) {
        try {
            Optional<User> optionalUser = userRepository.findById(userId);
            if (optionalUser.isPresent()) {
                return new ResponseEntity<>(optionalUser.get(), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(new ResponseMessage("User not found", HttpStatus.NOT_FOUND.value()), HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(new ResponseMessage("Error retrieving user: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<?> getAllUsers() {
        try {
            List<User> users = userRepository.findAll();
            if (users.isEmpty()) {
                return new ResponseEntity<>(new ResponseMessage("No users found", HttpStatus.NOT_FOUND.value()),HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(users, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ResponseMessage("Error retrieving users: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @Override
    public ResponseEntity<?> addProductToCart(Long userId, Long productId) {
        try {
            Optional<User> optionalUser = userRepository.findById(userId);
            if (!optionalUser.isPresent()) {
                return new ResponseEntity<>(new ResponseMessage("User not found", HttpStatus.NOT_FOUND.value()), HttpStatus.NOT_FOUND);
            }
            User user = optionalUser.get();

            
            Product product = productRepository.findById(productId).orElse(null);
            if (product == null) {
                return new ResponseEntity<>(new ResponseMessage("Product not found", HttpStatus.NOT_FOUND.value()), HttpStatus.NOT_FOUND);
            }
            
            Cart cart = user.getCart();
            if (cart == null) {
                return new ResponseEntity<>(new ResponseMessage("User's cart not found", HttpStatus.NOT_FOUND.value()), HttpStatus.NOT_FOUND);
            }
            
            cart.addProduct(product);
            cartRepository.save(cart);
            userRepository.save(user);

            return new ResponseEntity<>(new ResponseMessage("Product added to cart successfully", HttpStatus.OK.value()), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ResponseMessage("Error adding product to cart: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
}


