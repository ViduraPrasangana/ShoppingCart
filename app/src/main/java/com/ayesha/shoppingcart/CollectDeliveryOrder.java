package com.ayesha.shoppingcart;

import java.sql.Time;
import java.util.ArrayList;

public class CollectDeliveryOrder extends Order{

    private Time collectTime;
    private String collectAddress = "MAGA ONE, No 200 Narahenpita - Nawala Rd, 11222";


    public CollectDeliveryOrder(ArrayList<CartItem> cartItems, int totalPrice, Time collectTime) {
        super(cartItems, totalPrice);
        this.collectTime = collectTime;
    }

    public Time getCollectTime() {
        return collectTime;
    }

    public void setCollectTime(Time collectTime) {
        this.collectTime = collectTime;
    }
}
