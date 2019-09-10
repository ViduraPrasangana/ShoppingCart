package com.ayesha.shoppingcart;

//Categories!//
//--Frozen food
//--Grocery
//--Vegetables
//--Fruits
//--Meat
//--Fish
//--Liquor
//--Chilled

public class Category {

    private int id;
    private String name,image_url;

    public Category(){

    }

    public Category(int id, String image, String name) {
        this.id = id;
        this.image_url = image;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
