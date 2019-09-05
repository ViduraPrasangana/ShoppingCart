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

public class HomeFragment extends Fragment {
    private RecyclerView recyclerView;
    private AdapterProducts adpter;
    private Context context;
    private ArrayList<Product> productsList;
    private DatabaseReference dbRef;
    private ImageView logo;
    private Toolbar toolbar;

    private static HomeFragment instance;

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

    public void fetchProductsFromDB(){
        dbRef = FirebaseDatabase.getInstance().getReference("products/");
        dbRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ArrayList<Product> products = new ArrayList<>();

                for(DataSnapshot data: dataSnapshot.getChildren()){
                    System.out.println(data.toString());
                    products.add(data.getValue(Product.class));
                }
                updateRecyclerView(products);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }

    private void updateRecyclerView(ArrayList<Product> products){
        adpter = new AdapterProducts(products,context);
        recyclerView.setAdapter(adpter);
    }

    public static HomeFragment getInstance(){
        if(instance==null){
            instance = new HomeFragment();
        }
        return instance;
    }
}
