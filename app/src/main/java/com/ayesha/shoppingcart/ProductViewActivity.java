package com.ayesha.shoppingcart;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

public class ProductViewActivity extends AppCompatActivity {
    private String title,image_url;
    private double price;
    private int id;

    private ImageView imageView;
    private TextView titleView,priceView;

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
