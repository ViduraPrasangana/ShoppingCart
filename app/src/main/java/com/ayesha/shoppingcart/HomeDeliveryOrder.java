package com.ayesha.shoppingcart;

import java.util.ArrayList;

public class HomeDeliveryOrder extends Order {

    private String deliveryAddress;
    public String orderType = Constants.ORDERTYPE_DELIVERY;

    public HomeDeliveryOrder() {
    }

    public HomeDeliveryOrder(ArrayList<CartItem> cartItems, double totalPrice, String collectAddress, String name, String mobileNumber) {
        super(cartItems, totalPrice,name,mobileNumber);
        this.deliveryAddress = collectAddress;
    }

    public String getDeliveryAddress() {
        return deliveryAddress;
    }

    public void setDeliveryAddress(String deliveryAddress) {
        this.deliveryAddress = deliveryAddress;
    }


}
