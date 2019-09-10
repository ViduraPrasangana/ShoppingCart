package com.ayesha.shoppingcart;

public class CartItem {
    private int id;
    private int quantity;
    private double totalPrice;

    public CartItem() {
    }

    public CartItem(int id, int quantity, double totalPrice) {
        this.id = id;
        this.quantity = quantity;
        this.totalPrice = totalPrice;
    }
    public void setId(int id){
        this.id = id;
    }

    public int getId(){
        return id;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }
}
