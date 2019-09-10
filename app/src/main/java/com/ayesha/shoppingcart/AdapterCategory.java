package com.ayesha.shoppingcart;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
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
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class AdapterCategory extends RecyclerView.Adapter<AdapterCategory.ViewHolder> {

    private ArrayList<Category> categories;
    private Context context;
    private Toolbar toolbar;
    private ImageView logo;

    private DisplayImageOptions options;
    private ImageLoader imageLoader;

    public AdapterCategory(Context context, ArrayList<Category> categories) {
        this.categories = categories;
        this.context = context;

        toolbar = CategoryFragment.categoryFragment.toolbar;
        logo = CategoryFragment.categoryFragment.logo;

        options = new DisplayImageOptions.Builder().cacheOnDisk(true).build();
        imageLoader = ImageLoader.getInstance();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.holder_category, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        final Category category = categories.get(position); //This will hold the relevent Category object for the relevent Recyclcer View
        imageLoader.displayImage(category.getImage_url(), holder.image, options);
        holder.name.setText(category.getName());
        holder.name.setSelected(true);
        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Constants.showToast(context, category.getName());
                Pair<View, String> p1 = Pair.create((View) logo, "logo");
                Pair<View, String> p2 = Pair.create((View) toolbar, "toolbar");
                Pair<View, String> p3 = Pair.create((View) holder.name, "category");


                ActivityOptionsCompat option = ActivityOptionsCompat
                        .makeSceneTransitionAnimation((Activity) context, p1, p2,p3);
                Intent intent = new Intent(context, CategoryActivity.class);
                intent.putExtra("category_id", category.getId());
                intent.putExtra("category", category.getName());
                context.startActivity(intent, option.toBundle());

            }
        });

    }

    @Override
    public int getItemCount() {
        return categories.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        private CircleImageView image;
        private TextView name;
        private MaterialCardView layout;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.categoryImage);
            name = itemView.findViewById(R.id.categoryName);
            layout = itemView.findViewById(R.id.categoryRecyclerLayout);
        }
    }
}
