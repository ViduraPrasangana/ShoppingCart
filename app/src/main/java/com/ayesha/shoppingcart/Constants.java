package com.ayesha.shoppingcart;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

class Constants {

    static ArrayList<Product> allProducts;
    static ArrayList<Category> allCategories;
    static ArrayList<Product> allProducts2;
    static ArrayList<CartItem> cartItems;
    static User user;
    final static int RANDOM_PRODUCTS_COUNT = 6;

//    static Category frozenFood = new Category(0,R.mipmap.category_frozen_food,"Frozen Food");
//    static Category grocery = new Category(0,R.mipmap.category_grocery,"Grocery Items");
//    static Category vegetable = new Category(0,R.mipmap.category_vegetable,"Vegetables");
//    static Category fruit = new Category(0,R.mipmap.category_fruit,"Fruits");
//    static Category meat = new Category(0,R.mipmap.category_meat,"Meat");
//    static Category fish = new Category(0,R.mipmap.category_fish,"Fish");
//    static Category liquor = new Category(0,R.mipmap.category_liquor,"Liquor");
//    static Category chilled = new Category(0,R.mipmap.category_chilled,"Chilled Food Items");
    //static Category chilled2 = new Category(0,R.mipmap.category_chilled,"Chilled Food Items");

    static boolean categoriesFetched,productsFetched = false;

//    static ArrayList<Category> getCategoryArrayList(){
//        ArrayList<Category> categories = new ArrayList<>();
//
//        categories.add(frozenFood);
//        categories.add(grocery);
//        categories.add(vegetable);
//        categories.add(fruit);
//        categories.add(meat);
//        categories.add(fish);
//        categories.add(liquor);
//        categories.add(chilled);
//        //categories.add(chilled2);
//
//        return categories;
//    }

    public static void fetchTheCurrentUser(){
        DatabaseReference dbRef;
        dbRef = FirebaseDatabase.getInstance().getReference("users/"+FirebaseAuth.getInstance().getUid());
        dbRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                User tempUser = dataSnapshot.getValue(User.class);
                user = tempUser;
                //HomeFragment.homeFragment.loadRandomProducts();
                //activity.dialogDismiss();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }


    public static void fetchProductsFromDB(final MainActivity activity){
        DatabaseReference dbRef;
        dbRef = FirebaseDatabase.getInstance().getReference("products/");
        dbRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                allProducts = new ArrayList<>();
                for(DataSnapshot data: dataSnapshot.getChildren()){
                    allProducts.add(data.getValue(Product.class));
                }
                HomeFragment.homeFragment.loadRandomProducts();
                productsFetched = true;
                dialogDismiss(activity);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }

    private static void dialogDismiss(MainActivity activity){
        if(productsFetched && categoriesFetched){
            activity.dialogDismiss();
        }
    }

    static void fetchAllCategoriesFromDB(final MainActivity activity){
        DatabaseReference dbRef;
        dbRef = FirebaseDatabase.getInstance().getReference("categories/");
        dbRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                allCategories = new ArrayList<>();
                for(DataSnapshot data: dataSnapshot.getChildren()){
                    allCategories.add(data.getValue(Category.class));
                }
                CategoryFragment.categoryFragment.loadCategoriesToView(allCategories);
                categoriesFetched = true;
                dialogDismiss(activity);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }

    public static void fetchCartItemsFromDB(View view){
        DatabaseReference dbRef;
        dbRef = FirebaseDatabase.getInstance().getReference("carts/"+FirebaseAuth.getInstance().getUid()+"/");
        //"users/"+FirebaseAuth.getInstance().getUid() + "/"
        final View view1 = view;
        dbRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                cartItems = new ArrayList<>();
                for(DataSnapshot data: dataSnapshot.getChildren()){
                    cartItems.add(data.getValue(CartItem.class));
                    //CartFragment.productCart.add(data.getValue(Product.class));
                }

                CartFragment.cartFragment.initRecycleView(view1, cartItems);

                Log.d("Haaaaaaaaaa2",Integer.toString(cartItems.size()));
                CartFragment.cartFragment.setProductCart();
                Log.d("Haaaaaaaaaa3",Integer.toString(CartFragment.productCart.size()));

                //HomeFragment.homeFragment.loadRandomProducts();
                //activity.dialogDismiss();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }



    static void showToast(Context context, String text){
        Toast.makeText(context,text,Toast.LENGTH_SHORT).show();
    }
    static void showSnack(View view,String text){
        Snackbar.make(view,text,Snackbar.LENGTH_SHORT).show();
    }
    static void showRedSnack(View view,String text){
        Snackbar snackbar;
        snackbar = Snackbar.make(view,text,Snackbar.LENGTH_SHORT);
        View snackBarView = snackbar.getView();
        snackBarView.setBackgroundColor(0x00FF7F7F);
        snackbar.show();

    }
    static void showAlertBox(Context context, String title, String message){
        new AlertDialog.Builder(context)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // Continue with delete operation
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }
    static void openMain(Context context){
        Intent intent = new Intent(context,MainActivity.class);
        context.startActivity(intent);
    }
}
