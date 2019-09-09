package com.ayesha.shoppingcart;

import android.content.Context;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CategoryActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private AdapterProducts adapterProducts;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.category_layout);
        context = this;
        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new GridLayoutManager(context,3));

    }

    private void setRecyclerView(ArrayList<Product> arrayList){
        adapterProducts = new AdapterProducts(arrayList,context);
        recyclerView.setAdapter(adapterProducts);
    }
}

