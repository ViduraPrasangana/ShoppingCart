package com.ayesha.shoppingcart;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.mancj.materialsearchbar.MaterialSearchBar;

import java.util.ArrayList;

public class CategoryActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private AdapterProducts adapterProducts;
    private Context context;
    private Toolbar toolbar;
    private ImageView logo;
    private TextView title;
    private MaterialSearchBar searchBar;
    private ArrayList<Product> filteredArray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.category_layout);
        context = this;
        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new GridLayoutManager(context,3));

        searchBar = findViewById(R.id.search);
        title = findViewById(R.id.title);
        toolbar = findViewById(R.id.toolbar);
        logo = findViewById(R.id.logo);
        filteredArray = getFilteredProducts();
        updateRecyclerView(filteredArray);

        searchBar.addTextChangeListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                System.out.println(charSequence);
                if(charSequence.equals(" ") || charSequence.length()==0){
                    updateRecyclerView(filteredArray);
                }else {
                    searchProducts(charSequence.toString());
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        setTitle();
    }
    private void setTitle(){
        String titleText = getIntent().getStringExtra("category");
        title.setText(titleText);
    }

    private void updateRecyclerView(ArrayList<Product> arrayList){
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

    private void searchProducts(String search){
        ArrayList<Product> searchedProducts = new ArrayList<>();

        for(Product product: filteredArray){
            System.out.println(product.getName());
            if(product.getName().toLowerCase().contains(search.toLowerCase())){
                searchedProducts.add(product);
            }
        }
        updateRecyclerView(searchedProducts);
    }
}

