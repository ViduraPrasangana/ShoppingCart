package com.ayesha.shoppingcart;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityOptionsCompat;
import androidx.core.util.Pair;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;

//import android.util.Pair;

public class CartFragment extends Fragment {

    static ArrayList<Product> productCart = new ArrayList<>();
    static CartFragment cartFragment;
    private TextView price;
    private MaterialButton confirm;
    private RecyclerView productRecycleView;
    //private Toolbar toolbar;
    private Context context;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        CartFragment.cartFragment = this;
        context = requireContext();
        View view =  inflater.inflate(R.layout.cart_fragment,container,false);

        this.price = view.findViewById(R.id.price);
        this.confirm = view.findViewById(R.id.confirm);
        this.productRecycleView = view.findViewById(R.id.cartRecyclerView);
        //this.toolbar = view.findViewById(R.id.cartToolbar);

        Constants.fetchProductsFromDB2(view);

        this.confirmOnClickListner();
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
        CartFragment.productCart = productCart;
        this.price.setText(Double.toString(this.calculateTotalPrice(productCart)));
        RecyclerView recyclerView = view.findViewById(R.id.cartRecyclerView);
        RecyclerViewAdapterCart adapter = new RecyclerViewAdapterCart(getActivity(),productCart);
        recyclerView.setAdapter(adapter);
        //recyclerView.setLayoutManager(new GridLayoutManager(getActivity(),2));
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

    }

    private double calculateTotalPrice(ArrayList<Product> productCart){
        double tot = 0;
        for(Product product: productCart){
//            tot+=(product.getPrice()*Double.valueOf(product.getQuantity()));
        }
        return tot;
    }

    private void confirmOnClickListner(){
        this.confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Pair<View, String> p1 = Pair.create((View)CartFragment.this.productRecycleView,"recycleView");
                Pair<View, String> p2 = Pair.create((View)CartFragment.this.confirm, "confirm");
                //Pair<View, String> p3 = Pair.create((View)CartFragment.this.productRecycleView,"recycleView");
                //Pair<View, String> p4 = Pair.create((View)CartFragment.this.confirm, "confirm");


                ActivityOptionsCompat option = ActivityOptionsCompat.makeSceneTransitionAnimation((Activity) context,p1,p2,p1,p2);
                Intent intent = new Intent(CartFragment.this.getContext(), HolderBill.class);
                //Bundle bundle = new Bundle();

                intent.putExtra("price", CartFragment.this.price.getText());
                context.startActivity(intent,option.toBundle());
            }
        });
    }
}
//
//                Constants.showToast(context, "clicked");
//                        Pair<View, String> p1 = Pair.create((View) holder.imageView, "image");
//        Pair<View, String> p2 = Pair.create((View) holder.title, "title");
//        Pair<View, String> p3 = Pair.create((View) logo, "logo");
//        Pair<View, String> p4 = Pair.create((View) toolbar, "toolbar");
//
//        ActivityOptionsCompat option = ActivityOptionsCompat
//        .makeSceneTransitionAnimation((Activity) context, p1, p2,p3, p4);
//        Intent intent = new Intent(context, ProductViewActivity.class);
//        Bundle bundle = new Bundle();
//
//        intent.putExtra("product_id",product.getId());
//        intent.putExtra("title",product.getName());
//        intent.putExtra("price",product.getPrice());
//        intent.putExtra("image_url",product.getImage_url());
//        context.startActivity(intent, option.toBundle());
