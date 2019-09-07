package com.ayesha.shoppingcart;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.card.MaterialCardView;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class RecyclerViewAdapterCart extends RecyclerView.Adapter<RecyclerViewAdapterCart.ViewHolder>{

    private ArrayList<Product> products;
    private Context context;

    public RecyclerViewAdapterCart(Context context,ArrayList<Product> products) {
        this.products = products;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.holder_cart_product, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final Product tempProduct = products.get(position); //This will hold the relevent Category object for the relevent Recyclcer View
        DisplayImageOptions options = new DisplayImageOptions.Builder().cacheOnDisk(true).build();
        ImageLoader imageLoader = ImageLoader.getInstance();
        imageLoader.displayImage(
                tempProduct.getImage_url(),
                holder.image, options);
        holder.name.setText(tempProduct.getName());
        holder.price.setText(Double.toString(tempProduct.getPrice()));
        holder.quantity.setText(Integer.toString(tempProduct.getQuantity()));

    }

    @Override
    public int getItemCount() {
        return products.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder{
        private CircleImageView image;
        private TextView name;
        private TextView price;
        private TextView quantity;
        private MaterialButton close;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.imageView);
            name = itemView.findViewById(R.id.name);
            price = itemView.findViewById(R.id.price);
            quantity = itemView.findViewById(R.id.quantiity);
            close = itemView.findViewById(R.id.close);

        }
    }
}
