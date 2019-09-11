package com.ayesha.shoppingcart;

import java.util.ArrayList;

public class HomeDeliveryOrder extends Order {

    private String collectAddress;

    public HomeDeliveryOrder(ArrayList<CartItem> cartItems, int totalPrice, String collectAddress) {
        super(cartItems, totalPrice);
        this.collectAddress = collectAddress;
    }

    public String getCollectAddress() {
        return collectAddress;
    }

    public void setCollectAddress(String collectAddress) {
        this.collectAddress = collectAddress;
    }
}
