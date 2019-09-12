package com.ayesha.shoppingcart;

import java.util.ArrayList;

public class CollectDeliveryOrder extends Order{

    private String collectTimeLimit;
    private String collectAddress;
    public String orderType = Constants.ORDERTYPE_COLLECT;

    public CollectDeliveryOrder() {
    }

    public CollectDeliveryOrder(ArrayList<CartItem> cartItems, double totalPrice, String collectTime, String collectAddress, String name, String mobileNumber) {
        super(cartItems, totalPrice, name, mobileNumber);
        this.collectTimeLimit = collectTime;
        this.collectAddress = collectAddress;
    }

    public String getCollectTimeLimit() {
        return collectTimeLimit;
    }

    public void setCollectTimeLimit(String collectTimeLimit) {
        this.collectTimeLimit = collectTimeLimit;
    }

    public String getCollectAddress() {
        return collectAddress;
    }

    public void setCollectAddress(String collectAddress) {
        this.collectAddress = collectAddress;
    }
}
