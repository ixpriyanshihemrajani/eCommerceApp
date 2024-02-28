package com.app.eCommerceApp.model;


import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;


import java.util.HashSet;
import java.util.Set;

import java.util.Date;




@Entity
@Table(name = "ORDER_TABLE")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ORDER_ID")
    private Long id;
    @Column(name = "ORDER_NUMBER")
    private String orderNumber;
    @Column(name = "ORDER_DATE")
    private Date orderDate;

    @ManyToMany(cascade = { CascadeType.ALL })
    @JoinTable(
            name = "ORDER_PRODUCT",
            joinColumns = @JoinColumn(name = "ORDER_ID"),
            inverseJoinColumns = @JoinColumn(name = "PRODUCT_ID")
    )
    private Set<Product> products = new HashSet<Product>();

    @ManyToOne
    @JoinColumn(name="CART_ID")
    private Cart cartOrderRef;

	public Order() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Order(Long id, String orderNumber, Date orderDate, Set<Product> products, Cart cartOrderRef) {
		super();
		this.id = id;
		this.orderNumber = orderNumber;
		this.orderDate = orderDate;
		this.products = products;
		this.cartOrderRef = cartOrderRef;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getOrderNumber() {
		return orderNumber;
	}

	public void setOrderNumber(String orderNumber) {
		this.orderNumber = orderNumber;
	}

	public Date getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(Date orderDate) {
		this.orderDate = orderDate;
	}

	public Set<Product> getProducts() {
		return products;
	}

	public void setProducts(Set<Product> products) {
		this.products = products;
	}

	public Cart getCartOrderRef() {
		return cartOrderRef;
	}

	public void setCartOrderRef(Cart cartOrderRef) {
		this.cartOrderRef = cartOrderRef;
	}
    
	
}