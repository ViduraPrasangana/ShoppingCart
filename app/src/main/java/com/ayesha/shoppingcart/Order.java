package com.ayesha.shoppingcart;

import java.util.ArrayList;

public abstract class Order {
    private String orderId;
    private ArrayList<CartItem> cartItems;
    private double totalPrice;
    private String name;
    private String mobileNumber;

    public Order() {
    }

    public Order(ArrayList<CartItem> cartItems, double totalPrice, String name, String mobileNumber) {
        this.cartItems = cartItems;
        this.totalPrice = totalPrice;
        this.name = name;
        this.mobileNumber = mobileNumber;

        orderId = System.currentTimeMillis()+"-"+Constants.user.getFirstName();
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public ArrayList<CartItem> getCartItems() {
        return cartItems;
    }

    public void setCartItems(ArrayList<CartItem> cartItems) {
        this.cartItems = cartItems;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }
}
