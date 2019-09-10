package com.ayesha.shoppingcart;

import android.content.Context;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CategoryActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private AdapterProducts adapterProducts;
    private Context context;
    private Toolbar toolbar;
    private ImageView logo;
    private TextView title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.category_layout);
        context = this;
        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new GridLayoutManager(context,3));

        title = findViewById(R.id.title);
        toolbar = findViewById(R.id.toolbar);
        logo = findViewById(R.id.logo);
        setRecyclerView(getFilteredProducts());

        setTitle();
    }
    private void setTitle(){
        String titleText = getIntent().getStringExtra("category");
        title.setText(titleText);
    }

    private void setRecyclerView(ArrayList<Product> arrayList){
        adapterProducts = new AdapterProducts(arrayList,context,logo,toolbar);
        recyclerView.setAdapter(adapterProducts);
    }

    private ArrayList<Product> getFilteredProducts(){
        int productId = getIntent().getIntExtra("category_id",0);
        ArrayList<Product> filteredArray = new ArrayList<>();
        ArrayList<Product> allProducts = Constants.allProducts;
        for(Product product: allProducts){
            if(productId == product.getCategory()){
                filteredArray.add(product);
            }
        }
        return filteredArray;
    }
}

