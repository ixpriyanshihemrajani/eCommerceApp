package com.app.eCommerceApp.model;

import java.util.List;

public class OrderedProductDTO {
    private String username;
    private List<Long> orderIds;
    private String productName;
    private int productCount;
    
    

    // Constructor, getters, and setters

    public OrderedProductDTO() {
		super();
		// TODO Auto-generated constructor stub
	}



	public OrderedProductDTO(String username, List<Long> orderIds, String productName, int productCount) {
        this.username = username;
        this.orderIds = orderIds;
        this.productName = productName;
        this.productCount = productCount;
    }



	public String getUsername() {
		return username;
	}



	public void setUsername(String username) {
		this.username = username;
	}



	public List<Long> getOrderIds() {
		return orderIds;
	}



	public void setOrderIds(List<Long> orderIds) {
		this.orderIds = orderIds;
	}



	public String getProductName() {
		return productName;
	}



	public void setProductName(String productName) {
		this.productName = productName;
	}



	public int getProductCount() {
		return productCount;
	}



	public void setProductCount(int productCount) {
		this.productCount = productCount;
	}

 
    
}	