package com.ayesha.shoppingcart;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

class Constants {

    static Category frozenFood = new Category(0,R.mipmap.category_frozen_food,"Frozen Food");
    static Category grocery = new Category(0,R.mipmap.category_grocery,"Grocery Items");
    static Category vegetable = new Category(0,R.mipmap.category_vegetable,"Vegetables");
    static Category fruit = new Category(0,R.mipmap.category_fruit,"Fruits");
    static Category meat = new Category(0,R.mipmap.category_meat,"Meat");
    static Category fish = new Category(0,R.mipmap.category_fish,"Fish");
    static Category liquor = new Category(0,R.mipmap.category_liquor,"Liquor");
    static Category chilled = new Category(0,R.mipmap.category_chilled,"Chilled Food Items");
    static Category chilled2 = new Category(0,R.mipmap.category_chilled,"Chilled Food Items");

    static ArrayList<Category> getCategoryArrayList(){
        ArrayList<Category> categories = new ArrayList<>();

        categories.add(frozenFood);
        categories.add(grocery);
        categories.add(vegetable);
        categories.add(fruit);
        categories.add(meat);
        categories.add(fish);
        categories.add(liquor);
        categories.add(chilled);
        categories.add(chilled2);

        return categories;
    }

    static void showToast(Context context, String text){
        Toast.makeText(context,text,Toast.LENGTH_SHORT).show();
    }
    static void showSnack(View view,String text){
        Snackbar.make(view,text,Snackbar.LENGTH_SHORT).show();
    }
    static void openMain(Context context){
        Intent intent = new Intent(context,MainActivity.class);
        context.startActivity(intent);
    }
}
