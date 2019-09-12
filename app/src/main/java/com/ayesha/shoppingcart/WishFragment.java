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

import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class WishFragment extends Fragment {
    private RecyclerView recyclerView;
    private AdapterProducts adapter;
    private Context context;
    private ArrayList<Product> wishList;
    private ImageView logo;
    private Toolbar toolbar;
    private MaterialButton addToCartAll, removeAll;

    public static WishFragment wishFragment;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.wish_fragment, container, false);

        wishFragment = this;
        context = this.requireContext();
        recyclerView = view.findViewById(R.id.recycler_view);
        logo = view.findViewById(R.id.logo);
        toolbar = view.findViewById(R.id.toolbar);
        addToCartAll = view.findViewById(R.id.add_all_to_cart);
        removeAll = view.findViewById(R.id.remove_all);

        removeAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                removeAll();
            }
        });

        addToCartAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addAllToCart();
            }
        });

        recyclerView.setLayoutManager(new GridLayoutManager(context, 3));
        loadWishProducts();
        return view;
    }

    void loadWishProducts() {
        if(Constants.wishList!=null) {
            updateRecyclerView(Constants.wishList);
        }
    }

    private void updateRecyclerView(ArrayList<Product> products) {
        adapter = new AdapterProducts(products, context, logo,toolbar);
        recyclerView.setAdapter(adapter);
    }

    private void removeAll(){
        ArrayList<Product> products = new ArrayList<>(Constants.wishList);
        for(Product product: products){
            Constants.removeFromWishList(product);
        }
    }

    private void addAllToCart(){
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("carts/"+ FirebaseAuth.getInstance().getUid());
        for(Product product : Constants.wishList) {
            ref.child(String.valueOf(product.getId())).setValue(new CartItem(product.getId(), 1, product.getPrice()));
        }
        removeAll();
    }

}
