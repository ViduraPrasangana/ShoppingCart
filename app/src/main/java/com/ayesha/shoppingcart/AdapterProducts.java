package com.ayesha.shoppingcart;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityOptionsCompat;
import androidx.core.util.Pair;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.card.MaterialCardView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;

public class AdapterProducts extends RecyclerView.Adapter<AdapterProducts.ProductViewHolder> {
    private ArrayList<Product> productArrayList;
    private Context context;
    private ImageView logo;
    private Toolbar toolbar;

    public AdapterProducts(ArrayList<Product> products, Context context, ImageView imageView, Toolbar toolbarView) {
        productArrayList = products;
        this.context = context;
        logo = imageView;
        toolbar = toolbarView;
    }

    public static class ProductViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView title, price;
        MaterialCardView materialCardView;
        FloatingActionButton wishBtn;

        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.image);
            title = itemView.findViewById(R.id.title);
            price = itemView.findViewById(R.id.price);
            materialCardView = itemView.findViewById(R.id.card);
            wishBtn = itemView.findViewById(R.id.wishBtn);
            wishBtn.setAlpha(0.55f);
        }

    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.holder_product, parent, false);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ProductViewHolder holder, final int position) {
        final Product product = productArrayList.get(position);
        DisplayImageOptions options = new DisplayImageOptions.Builder().cacheOnDisk(true).build();
        ImageLoader imageLoader = ImageLoader.getInstance();
        imageLoader.displayImage(
                product.getImage_url(),
                holder.imageView, options);
        holder.title.setText(product.getName());
        holder.title.setSelected(true);
        String price = String.valueOf(product.getPrice());
        holder.price.setText(price);
        holder.materialCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Pair<View, String> p1 = Pair.create((View) holder.imageView, "image");
                Pair<View, String> p2 = Pair.create((View) holder.title, "title");
                Pair<View, String> p3 = Pair.create((View) logo, "logo");
                Pair<View, String> p4 = Pair.create((View) toolbar, "toolbar");

                ActivityOptionsCompat option = ActivityOptionsCompat
                        .makeSceneTransitionAnimation((Activity) context, p1, p2,p3, p4);
                Intent intent = new Intent(context, ProductViewActivity.class);
                Bundle bundle = new Bundle();

                intent.putExtra("product_id",product.getId());
                intent.putExtra("title",product.getName());
                intent.putExtra("price",product.getPrice());
                intent.putExtra("image_url",product.getImage_url());
                context.startActivity(intent, option.toBundle());
            }
        });

        if(!Constants.isInWishList(product.getId())){
            holder.wishBtn.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_playlist_add_black_24dp));
            holder.wishBtn.setBackgroundTintList(ColorStateList.valueOf(context.getResources().getColor(R.color.wish_btn_normal)));
        }else {
            holder.wishBtn.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_playlist_add_check_black_24dp));
            holder.wishBtn.setBackgroundTintList(ColorStateList.valueOf(context.getResources().getColor(R.color.wish_btn_checked)));
        }

        holder.wishBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(Constants.isInWishList(product.getId())){
                    holder.wishBtn.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_playlist_add_black_24dp));
                    holder.wishBtn.setBackgroundTintList(ColorStateList.valueOf(context.getResources().getColor(R.color.wish_btn_normal)));
                    Constants.removeFromWishList(product.getId());
                    Constants.showSnack(holder.materialCardView,"Removed from wish list");
                }else {
                    holder.wishBtn.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_playlist_add_check_black_24dp));
                    holder.wishBtn.setBackgroundTintList(ColorStateList.valueOf(context.getResources().getColor(R.color.wish_btn_checked)));
                    Constants.addToWishList(product.getId());
                    Constants.showSnack(holder.materialCardView,"Added to wish list");
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        if(productArrayList!=null)
        return productArrayList.size();
        return  0;
    }

}