package com.ayesha.shoppingcart;

public class CartItem {
    private int productId;
    private int quantity;
    private double totalPrice;

    public CartItem() {
    }

    public CartItem(int productId, int quantity, double totalPrice) {
        this.productId = productId;
        this.quantity = quantity;
        this.totalPrice = totalPrice;
    }
    public void setProductId(int productId){
        this.productId = productId;
    }

    public int getProductId(){
        return productId;
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
