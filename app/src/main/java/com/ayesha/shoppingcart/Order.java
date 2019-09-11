package com.ayesha.shoppingcart;

import java.util.ArrayList;

public abstract class Order {
    private ArrayList<CartItem> cartItems;
    private int totalPrice;

    public Order(ArrayList<CartItem> cartItems, int totalPrice) {
        this.cartItems = cartItems;
        this.totalPrice = totalPrice;
    }

    public ArrayList<CartItem> getCartItems() {
        return cartItems;
    }

    public void setCartItems(ArrayList<CartItem> cartItems) {
        this.cartItems = cartItems;
    }

    public int getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(int totalPrice) {
        this.totalPrice = totalPrice;
    }
}
