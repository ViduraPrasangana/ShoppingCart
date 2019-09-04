package com.ayesha.shoppingcart;

public class Product {
    private String name, description, imgUrl;
    private double price;

    public Product(String name, String description, String imgUrl, double price) {
        this.name = name;
        this.description = description;
        this.imgUrl = imgUrl;
        this.price = price;
    }
}
