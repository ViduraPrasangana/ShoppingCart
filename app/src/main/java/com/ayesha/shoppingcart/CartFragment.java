package com.ayesha.shoppingcart;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CartFragment extends Fragment {

    static ArrayList<Product> productCart = new ArrayList<>();
    static CartFragment cartFragment;

    @Override
    public void onStart() {

        super.onStart();
        //Constants.fetchProductsFromDB2();
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        CartFragment.cartFragment = this;

        View view =  inflater.inflate(R.layout.cart_fragment,container,false);
        Constants.fetchProductsFromDB2(view);
        //initProducts(view);
        return view;
    }

    private void initProducts(View view){

        //Constants.fetchProductsFromDB2();
        Log.d("Haaaaaaaaaa",Integer.toString(CartFragment.productCart.size()));
        //this.productCart = Constants.allProducts2;
        //this.initRecycleView(view);
    }

    void setProductCart(){
        CartFragment.productCart = Constants.allProducts2;
    }

    void initRecycleView(View view, ArrayList<Product> productCart){
        RecyclerView recyclerView = view.findViewById(R.id.cartRecyclerView);
        RecyclerViewAdapterCart adapter = new RecyclerViewAdapterCart(getActivity(),productCart);
        recyclerView.setAdapter(adapter);
        //recyclerView.setLayoutManager(new GridLayoutManager(getActivity(),2));
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

    }
}
