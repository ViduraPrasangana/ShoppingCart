package com.ayesha.shoppingcart;
import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class User {
     public String email, firstName, lastName, number, address;

     public User(){

     }

     User(String email, String firstName, String lastName, String number, String address) {
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.number = number;
        this.address = address;
    }
}
