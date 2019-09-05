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

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;

public class HomeFragment extends Fragment {
    private RecyclerView recyclerView;
    private AdapterProducts adapter;
    private Context context;
    private ArrayList<Product> productsList;
    private DatabaseReference dbRef;
    private ImageView logo;
    private Toolbar toolbar;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.home_fragment,container,false);

        context = this.requireContext();
        recyclerView = view.findViewById(R.id.recycler_view);
        logo = view.findViewById(R.id.logo);
        toolbar = view.findViewById(R.id.toolbar);
        AdapterProducts.setViews(logo,toolbar);
        recyclerView.setLayoutManager(new GridLayoutManager(context,3));

        fetchProductsFromDB();
        return view;
    }

    private void fetchProductsFromDB(){
        dbRef = FirebaseDatabase.getInstance().getReference("products/");
        dbRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ArrayList<Product> products = new ArrayList<>();
                for(DataSnapshot data: dataSnapshot.getChildren()){
                    products.add(data.getValue(Product.class));
                }
                updateRecyclerView(getRandomProducts(products));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }

    private void updateRecyclerView(ArrayList<Product> products){
        adapter = new AdapterProducts(products,context);
        recyclerView.setAdapter(adapter);
    }

    private ArrayList<Product> getRandomProducts(ArrayList<Product> products){
        ArrayList<Product> shuffledProducts = new ArrayList<>(products);
        Collections.shuffle(shuffledProducts);
        return new ArrayList<>(shuffledProducts.subList(0,Constants.RANDOM_PRODUCTS_COUNT));
    }
}
