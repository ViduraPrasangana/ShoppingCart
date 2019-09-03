package com.ayesha.shoppingcart;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

class Constants {
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
