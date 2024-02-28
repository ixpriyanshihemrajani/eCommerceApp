package com.app.eCommerceApp.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.app.eCommerceApp.model.Product;
import com.app.eCommerceApp.repository.ProductRepository;
import com.app.eCommerceApp.repository.CartRepository;
import com.app.eCommerceApp.response.ResponseMessage;

@Service
public class ProductServiceImpl implements ProductService {
    
    @Autowired
    private ProductRepository productRepository;
    
    @Autowired
    private CartRepository cartRepository; 
    
    @Override
    public ResponseEntity<?> createProduct(Product product){
        try {
            Product createdProduct = productRepository.save(product);
            return new ResponseEntity<>(createdProduct, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(new ResponseMessage("Error creating product: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @Override
    public ResponseEntity<?> updateProduct(Long productId, Product product) {
        try {
            boolean productInCart = cartRepository.existsByProductsId(productId);
            if (productInCart) {
                return new ResponseEntity<>(new ResponseMessage("The product is in a cart and cannot be updated", HttpStatus.BAD_REQUEST.value()), HttpStatus.BAD_REQUEST);
            }
            
            Optional<Product> optionalProduct = productRepository.findById(productId);
            if (optionalProduct.isPresent()) {
                product.setId(productId);
                productRepository.save(product);
                return new ResponseEntity<>(new ResponseMessage("Product updated successfully", HttpStatus.OK.value()), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(new ResponseMessage("Product not found", HttpStatus.NOT_FOUND.value()), HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(new ResponseMessage("Error updating product: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<?> deleteProduct(Long productId) {
        try {
            // Check if the product is in any cart
            boolean productInCart = cartRepository.existsByProductsId(productId);
            if (productInCart) {
                return new ResponseEntity<>(new ResponseMessage("The product is in a cart and cannot be deleted", HttpStatus.BAD_REQUEST.value()), HttpStatus.BAD_REQUEST);
            }
            
            Optional<Product> optionalProduct = productRepository.findById(productId);
            if (optionalProduct.isPresent()) {
                productRepository.deleteById(productId);
                return new ResponseEntity<>(new ResponseMessage("Product deleted successfully", HttpStatus.OK.value()),  HttpStatus.OK);
            } else {
                return new ResponseEntity<>(new ResponseMessage("Product not found", HttpStatus.NOT_FOUND.value()), HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(new ResponseMessage("Error deleting product: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    
    @Override
    public ResponseEntity<?> getProductById(Long productId) {
        try {
            Optional<Product> optionalProduct = productRepository.findById(productId);
            if (optionalProduct.isPresent()) {
                return new ResponseEntity<>(optionalProduct.get(), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(new ResponseMessage("Product not found", HttpStatus.NOT_FOUND.value()), HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(new ResponseMessage("Error retrieving product: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @Override
    public ResponseEntity<?> getAllProducts() {
        try {
            List<Product> products = productRepository.findAll();
            if (products.isEmpty()) {
                return new ResponseEntity<>(new ResponseMessage("No Products found", HttpStatus.NOT_FOUND.value()),HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(products, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ResponseMessage("Error retrieving products: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    
}
