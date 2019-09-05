package com.ayesha.shoppingcart;

import android.content.Context;
import android.os.Bundle;
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

import java.util.ArrayList;
import java.util.Collections;

public class HomeFragment extends Fragment {
    private RecyclerView recyclerView;
    private AdapterProducts adapter;
    private Context context;
    private ArrayList<Product> randomProductsList;
    private ImageView logo;
    private Toolbar toolbar;

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
}
