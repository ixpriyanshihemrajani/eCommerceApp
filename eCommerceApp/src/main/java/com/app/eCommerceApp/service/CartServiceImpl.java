package com.app.eCommerceApp.service;

import com.app.eCommerceApp.model.Cart;
import com.app.eCommerceApp.repository.CartRepository;
import com.app.eCommerceApp.response.ResponseMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CartServiceImpl implements CartService {

    @Autowired
    private CartRepository cartRepository;
    
    
    @Override
    public ResponseEntity<?> createCart(Cart cart){
        try {
            Cart createdCart = cartRepository.save(cart);
            return new ResponseEntity<>(createdCart, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(new ResponseMessage("Error creating Cart: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @Override
    public ResponseEntity<?> updateCart(Long cartId, Cart cart) {
        try {
            Optional<Cart> optionalCart = cartRepository.findById(cartId);
            if (optionalCart.isPresent()) {
                cart.setId(cartId);
                cartRepository.save(cart);
                return new ResponseEntity<>(new ResponseMessage("Cart updated successfully",HttpStatus.OK.value()), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(new ResponseMessage("Cart not found",HttpStatus.NOT_FOUND.value()), HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(new ResponseMessage("Error updating cart: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    
    @Override
    public ResponseEntity<?> deleteCart(Long cartId) {
        try {
            if (cartRepository.existsById(cartId)) {
                cartRepository.deleteById(cartId);
                return new ResponseEntity<>(new ResponseMessage("Cart deleted successfully",HttpStatus.OK.value()), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(new ResponseMessage("Cart not found",HttpStatus.NOT_FOUND.value()), HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(new ResponseMessage("Error deleting cart: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    
    @Override
    public ResponseEntity<?> getCartById(Long cartId) {
        try {
            Optional<Cart> optionalCart = cartRepository.findById(cartId);
            if (optionalCart.isPresent()) {
                return new ResponseEntity<>(optionalCart.get(), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(new ResponseMessage("Cart not found", HttpStatus.NOT_FOUND.value()), HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(new ResponseMessage("Error retrieving cart: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    
    @Override
    public ResponseEntity<?> getAllCarts() {
        try {
            List<Cart> carts = cartRepository.findAll();
            if (carts.isEmpty()) {
                return new ResponseEntity<>(new ResponseMessage("No carts found", HttpStatus.NOT_FOUND.value()),HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(carts, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ResponseMessage("Error retrieving carts: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
}  

    
