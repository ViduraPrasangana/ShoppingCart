package com.ayesha.shoppingcart;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.mancj.materialsearchbar.MaterialSearchBar;

import java.util.ArrayList;
import java.util.Collections;

public class HomeFragment extends Fragment {
    private RecyclerView recyclerView;
    private AdapterProducts adapter;
    private Context context;
    private ArrayList<Product> randomProductsList;
    private ImageView logo;
    private Toolbar toolbar;
    private MaterialSearchBar materialSearchBar;

    public static HomeFragment homeFragment;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.home_fragment, container, false);

        homeFragment = this;
        context = this.requireContext();
        recyclerView = view.findViewById(R.id.recycler_view);
        logo = view.findViewById(R.id.logo);
        toolbar = view.findViewById(R.id.toolbar);
        materialSearchBar = view.findViewById(R.id.search);

        materialSearchBar.addTextChangeListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                System.out.println(charSequence);
                if(charSequence.equals(" ") || charSequence.length()==0){
                    updateRecyclerView(randomProductsList);
                }else {
                    searchProducts(charSequence.toString());
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        AdapterProducts.setViews(logo, toolbar);
        recyclerView.setLayoutManager(new GridLayoutManager(context, 3));

        return view;
    }

    void loadRandomProducts() {
        if(Constants.allProducts!=null) {
            randomProductsList = getRandomProducts(Constants.allProducts);
            updateRecyclerView(randomProductsList);
        }
    }

    private void updateRecyclerView(ArrayList<Product> products) {
        adapter = new AdapterProducts(products, context);
        recyclerView.setAdapter(adapter);
    }

    private ArrayList<Product> getRandomProducts(ArrayList<Product> products) {
        ArrayList<Product> shuffledProducts = new ArrayList<>(products);
        Collections.shuffle(shuffledProducts);
        return new ArrayList<>(shuffledProducts.subList(0, Constants.RANDOM_PRODUCTS_COUNT));
    }

    private void searchProducts(String search){
        ArrayList<Product> allProducts = Constants.allProducts;
        ArrayList<Product> searchedProducts = new ArrayList<>();

        for(Product product: allProducts){
            System.out.println(product.getName());
            if(product.getName().toLowerCase().contains(search.toLowerCase())){
                searchedProducts.add(product);
            }
        }
        updateRecyclerView(searchedProducts);
    }
}
