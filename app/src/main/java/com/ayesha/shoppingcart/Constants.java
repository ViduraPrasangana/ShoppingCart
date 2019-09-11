package com.ayesha.shoppingcart;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
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
import java.util.Arrays;

class Constants {

    static ArrayList<Product> allProducts;
    static ArrayList<Category> allCategories;
    static ArrayList<Product> allProducts2;
    static ArrayList<CartItem> cartItems;
    static User user;
    final static int RANDOM_PRODUCTS_COUNT = 9;

    static ArrayList<Product> wishList;

    static boolean categoriesFetched,productsFetched, wishlistFetched, cartFetched = false;

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
        if(productsFetched && categoriesFetched && wishlistFetched && cartFetched){
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

    public static void fetchCartItemsFromDB(final MainActivity mainActivity){
        DatabaseReference dbRef;
        dbRef = FirebaseDatabase.getInstance().getReference("carts/"+FirebaseAuth.getInstance().getUid()+"/");
        dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                cartItems = new ArrayList<>();
                for(DataSnapshot data: dataSnapshot.getChildren()){
                    cartItems.add(data.getValue(CartItem.class));
                }
                cartFetched = true;
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        CartFragment.cartFragment.initRecycleView(cartItems);
                    }
                },1000);
                dialogDismiss(mainActivity);
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

    static void addToWishList(int product_id){
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("wishlists/"+FirebaseAuth.getInstance().getUid());
        ref.child(String.valueOf(product_id)).setValue(new WishItem(product_id));
        wishList.add(findProductFromAll(product_id));
    }

    static void removeFromWishList(Product product){
        wishList.remove(product);
        removeFromWishList(product.getId());
    }
    static void removeFromWishList(int product_id){
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("wishlists/"+FirebaseAuth.getInstance().getUid());
        ref.child(String.valueOf(product_id)).removeValue();
    }
    static boolean isInWishList(int product_id){
        for(Product product : wishList){
            if(product.getId()==product_id){
                return true;
            }
        }
        return false;
    }
    static Product findProductFromAll(int product_id){
        for(Product product: allProducts){
            if(product.getId()==product_id){
                return product;
            }
        }
        return null;
    }
    static void fetchWishListFromDB(final MainActivity activity){
        wishList = new ArrayList<>();
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("wishlists/"+FirebaseAuth.getInstance().getUid());
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                wishList = new ArrayList<>();
                for (DataSnapshot data: dataSnapshot.getChildren()){
                    WishItem item = data.getValue(WishItem.class);
                    if(item!=null) wishList.add(findProductFromAll(item.getPriduct_id()));
                }
                System.out.println(Arrays.deepToString(wishList.toArray()));
                wishlistFetched=true;
                dialogDismiss(activity);
                if(WishFragment.wishFragment!=null) WishFragment.wishFragment.loadWishProducts();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
