package com.ayesha.shoppingcart;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

//import android.util.Pair;

public class CartFragment extends Fragment {

    static ArrayList<CartItem> productCart = new ArrayList<>();
    static CartFragment cartFragment;
    private static View cartView;
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
        cartView = view;
        this.price = view.findViewById(R.id.price);
        this.confirm = view.findViewById(R.id.confirm);
        this.productRecycleView = view.findViewById(R.id.cartRecyclerView);
        //this.toolbar = view.findViewById(R.id.cartToolbar);

        Constants.fetchCartItemsFromDB(view);

        this.confirmOnClickListner();
        //initProducts(view);
        return view;
    }

    public void onResume() {

        super.onResume();
        Constants.fetchCartItemsFromDB(cartView);


    }

    private void initProducts(View view){

        //Constants.fetchProductsFromDB2();
        Log.d("Haaaaaaaaaa",Integer.toString(CartFragment.productCart.size()));
        //this.productCart = Constants.allProducts2;
        //this.initRecycleView(view);
    }

    void setProductCart(){
        CartFragment.productCart = Constants.cartItems;
    }

    void initRecycleView(View view, final ArrayList<CartItem> productCart){
        CartFragment.productCart = productCart;
        this.price.setText(Double.toString(this.calculateTotalPrice(productCart)));
        RecyclerView recyclerView = view.findViewById(R.id.cartRecyclerView);
        final RecyclerViewAdapterCart adapter = new RecyclerViewAdapterCart(getActivity(),productCart);


        Log.d("SIZEEEEEEEEEEEEE", Integer.toString(productCart.size()));
        adapter.setClickListner(new RecyclerViewAdapterCart.OnItemClickListner() {
            @Override
            public void onDeleteClick(int position) {
                DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference("carts/").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child(Integer.toString(productCart.get(position).getId()));
                dbRef.removeValue();
                productCart.remove(position);
                adapter.notifyItemRemoved(position);
                price.setText(Double.toString(calculateTotalPrice(productCart)));
            }
        });

        recyclerView.setAdapter(adapter);
        //recyclerView.setLayoutManager(new GridLayoutManager(getActivity(),2));
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

    }

    private double calculateTotalPrice(ArrayList<CartItem> productCart){
        double tot = 0;
        for(CartItem cartItem: productCart){
//            tot+=(product.getPrice()*Double.valueOf(product.getQuantity()));
            tot+=cartItem.getTotalPrice();
        }
        return tot;
    }

    private void confirmOnClickListner(){
        this.confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                new AlertDialog.Builder(context)
                        .setTitle("Type of the delivery!")
                        .setMessage("Please select the type of delivery you want!")
                        .setPositiveButton("Collect Delivery", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                Intent intent = new Intent(CartFragment.this.getContext(), CollectDeliveryActivity.class);
                                intent.putExtra("price", CartFragment.this.price.getText());
                                context.startActivity(intent);
                            }
                        })
                        .setNeutralButton("Home Delivery", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                Intent intent = new Intent(CartFragment.this.getContext(), HomeDeliveryActivity.class);
                                //intent.putExtra("price", CartFragment.this.price.getText());
                                context.startActivity(intent);
                            }
                        })
                        .setIcon(android.R.drawable.ic_dialog_info)
                        .show();

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
