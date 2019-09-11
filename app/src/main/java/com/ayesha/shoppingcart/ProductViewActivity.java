package com.ayesha.shoppingcart;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import org.jetbrains.annotations.NotNull;

import it.sephiroth.android.library.numberpicker.NumberPicker;

public class ProductViewActivity extends AppCompatActivity {
    private String title,image_url;
    private double price;
    private int id;
    private int quantity =1;

    private ImageView imageView;
    private TextView titleView,priceView,totalPriceView;
    private NumberPicker numberPicker;

    private MaterialButton addToCartButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.product_view_layout);
        title = getIntent().getStringExtra("title");
        image_url = getIntent().getStringExtra("image_url");
        id = getIntent().getIntExtra("product_id",0);
        price = getIntent().getDoubleExtra("price",0);

        imageView = findViewById(R.id.imageView);
        titleView = findViewById(R.id.title);
        priceView = findViewById(R.id.unit_price);
        numberPicker = findViewById(R.id.numberPicker);
        totalPriceView = findViewById(R.id.total_price);
        totalPriceView.setText(String.valueOf(price));
        addToCartButton = findViewById(R.id.addToCart);

        numberPicker.setNumberPickerChangeListener(new NumberPicker.OnNumberPickerChangeListener() {
            @Override
            public void onProgressChanged(@NotNull NumberPicker numberPicker, int i, boolean b) {
                totalPriceView.setText(String.valueOf(price*i));
                quantity = i;
            }

            @Override
            public void onStartTrackingTouch(@NotNull NumberPicker numberPicker) {

            }

            @Override
            public void onStopTrackingTouch(@NotNull NumberPicker numberPicker) {

            }
        });

        addToCartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseReference ref = FirebaseDatabase.getInstance().getReference("carts/"+ FirebaseAuth.getInstance().getUid());
                ref.child(String.valueOf(id)).setValue(new CartItem(id,quantity,quantity*price));
            }
        });

        bindToView();
    }

    private void bindToView (){
        DisplayImageOptions options = new DisplayImageOptions.Builder().cacheOnDisk(true).build();
        ImageLoader imageLoader = ImageLoader.getInstance();
        imageLoader.displayImage(
                image_url,
                imageView, options);

        titleView.setText(title);
        String price2 = "Unit price: "+ price;
        priceView.setText(price2);
    }
}
