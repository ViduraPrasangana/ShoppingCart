package com.ayesha.shoppingcart;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
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
    static ArrayList<CartItem> cartItems;
    static User user;
    final static int RANDOM_PRODUCTS_COUNT = 9;
    final static String ORDERTYPE_COLLECT = "COLLECT";
    final static String ORDERTYPE_DELIVERY = "DELIVERY";

    static int BLUR_CURRENT_VALUE = 0;
    static int BLUR_FINAL_VALUE_SIGNUP = 10;
    static int BLUR_FINAL_VALUE_LOGIN = 0;
    static int ANIMATION_DURATION = 1000;

    static ArrayList<Product> wishList;

    static boolean categoriesFetched,productsFetched, wishlistFetched, cartFetched = false;

    public static void fetchTheCurrentUser(){
        DatabaseReference dbRef;
        dbRef = FirebaseDatabase.getInstance().getReference("users/"+FirebaseAuth.getInstance().getUid());
        dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                user = dataSnapshot.getValue(User.class);
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
        ref.child(String.valueOf(product_id)).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    HomeFragment.homeFragment.reloadAdapter();
                }
            }
        });
    }
    static boolean isInCart(int product_id){
        for(CartItem product : cartItems){
            if(product.getProductId()==product_id){
                System.out.println("still in");
                return true;
            }
        }
        return false;
    }
    static boolean isInWishList(int product_id){
        for(Product product : wishList){
            if(product.getId()==product_id){
                return true;
            }
        }
        return false;
    }

    static void removeCartItemFromArray(int product_id){
        for(CartItem item: cartItems){
            if(product_id== item.getProductId()){
                cartItems.remove(item);
                System.out.println("removed");
                break;
            }
        }
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
