package com.app.eCommerceApp.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.app.eCommerceApp.model.Cart;
import com.app.eCommerceApp.model.Order;
import com.app.eCommerceApp.model.OrderedProductDTO;
import com.app.eCommerceApp.model.Product;
import com.app.eCommerceApp.repository.CartRepository;
import com.app.eCommerceApp.repository.OrderRepository;
import com.app.eCommerceApp.response.ResponseMessage;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Override
    public ResponseEntity<?> orderProductsInCart(Long userId) {
        try {
            Optional<Cart> optionalCart = cartRepository.findByUserId(userId);
            if (optionalCart.isPresent()) {
                Cart cart = optionalCart.get();

                if (!cart.getProducts().isEmpty()) { // Ensure there are products in the cart
                    Order order = new Order();
                    order.setOrderDate(new Date());
                    order.setOrderNumber(generateOrderNumber());
                    
                    // Create a new set of products to avoid shared references
                    Set<Product> products = new HashSet<>(cart.getProducts());
                    order.setProducts(products);
                    order.setCartOrderRef(cart);

                    orderRepository.save(order);

                    cart.getProducts().clear();
                    cart.setTotal(0);
                    cartRepository.save(cart);

                    return new ResponseEntity<>(new ResponseMessage("Order placed successfully", HttpStatus.OK.value()), HttpStatus.OK);
                } else {
                    return new ResponseEntity<>(new ResponseMessage("Cart is empty", HttpStatus.BAD_REQUEST.value()), HttpStatus.BAD_REQUEST);
                }
            } else {
                return new ResponseEntity<>(new ResponseMessage("Cart not found", HttpStatus.NOT_FOUND.value()), HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(new ResponseMessage("Error ordering products: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private String generateOrderNumber() {
        return "ORD-" + System.currentTimeMillis();
    }
    
    
    @Override
    public ResponseEntity<?> getAllOrders() {
        try {
            List<Order> orders = orderRepository.findAll();
            if (orders.isEmpty()) {
                return new ResponseEntity<>(new ResponseMessage("No Orders found", HttpStatus.NOT_FOUND.value()),HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(orders, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ResponseMessage("Error retrieving orders: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    
    @Override
    public ResponseEntity<?> getOrderedProducts(Long userId) {
        try {
            // Fetch orders for the user from the repository
            List<Order> orders = orderRepository.findByCartOrderRef_User_Id(userId);
            
            // Create a map to store product counts
            Map<Product, Map<String, List<Long>>> productOrders = new HashMap<>();
            
            // Iterate through orders and group by product
            for (Order order : orders) {
                Set<Product> products = order.getProducts();
                for (Product product : products) {
                    Map<String, List<Long>> orderIdsMap = productOrders.computeIfAbsent(product, k -> new HashMap<>());
                    String username = order.getCartOrderRef().getUser().getUsername();
                    Long orderId = order.getId();
                    List<Long> orderIds = orderIdsMap.computeIfAbsent(username, k -> new ArrayList<>());
                    orderIds.add(orderId);
                }
            }
            
            // Create DTOs for the response
            List<OrderedProductDTO> orderedProducts = new ArrayList<>();
            for (Map.Entry<Product, Map<String, List<Long>>> entry : productOrders.entrySet()) {
                Product product = entry.getKey();
                Map<String, List<Long>> orderIdsMap = entry.getValue();
                for (Map.Entry<String, List<Long>> userOrders : orderIdsMap.entrySet()) {
                    String username = userOrders.getKey();
                    List<Long> orderIds = userOrders.getValue();
                    int productCount = orderIds.size();
                    orderedProducts.add(new OrderedProductDTO(username, orderIds, product.getName(), productCount));
                }
            }
            
            return new ResponseEntity<>(orderedProducts, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ResponseMessage("Error fetching ordered products: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
