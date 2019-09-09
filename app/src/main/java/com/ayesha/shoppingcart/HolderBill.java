package com.ayesha.shoppingcart;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;

public class HolderBill extends AppCompatActivity {

    private String price;
    private TextView priceView;
    private MaterialButton homeButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.price = getIntent().getStringExtra("price");
        setContentView(R.layout._holder_bill);

        priceView = findViewById(R.id.price);
        homeButton = findViewById(R.id.homeButton);

        bindToView();

        this.setHomeButtonOnClickListner();
    }

    private void bindToView (){
        String price2 = "Total price: "+ price;
        priceView.setText(price2);
    }

    private void setHomeButtonOnClickListner(){
        this.homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent intent = new Intent(HolderBill.this,MainActivity.class);
//                startActivity(intent);
                MainActivity.mainActivity.setPage(0);
                finish();

            }
        });
    }

}

//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.product_view_layout);
//        title = getIntent().getStringExtra("title");
//        image_url = getIntent().getStringExtra("image_url");
//        id = getIntent().getIntExtra("product_id",0);
//        price = getIntent().getDoubleExtra("price",0);
//
//        imageView = findViewById(R.id.imageView);
//        titleView = findViewById(R.id.title);
//        priceView = findViewById(R.id.unit_price);
//
//        bindToView();
//    }
//
//    private void bindToView (){
//        DisplayImageOptions options = new DisplayImageOptions.Builder().cacheOnDisk(true).build();
//        ImageLoader imageLoader = ImageLoader.getInstance();
//        imageLoader.displayImage(
//                image_url,
//                imageView, options);
//
//        titleView.setText(title);
//        String price2 = "Unit price: "+ price;
//        priceView.setText(price2);
//    }
