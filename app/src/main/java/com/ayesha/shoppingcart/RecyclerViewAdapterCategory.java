package com.ayesha.shoppingcart;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class RecyclerViewAdapterCategory extends RecyclerView.Adapter<RecyclerViewAdapterCategory.ViewHolder>{

    private ArrayList<Category> categories;
    private Context context;

    public RecyclerViewAdapterCategory(Context context,ArrayList<Category> categories) {
        this.categories = categories;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final Category tempCategory = categories.get(position); //This will hold the relevent Category object for the relevent Recyclcer View
        holder.image.setImageResource(tempCategory.getImage());
        holder.name.setText(tempCategory.getName());
        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Constants.showToast(context,tempCategory.getName());
            }
        });
    }

    @Override
    public int getItemCount() {
        return categories.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder{
        private CircleImageView image;
        private TextView name;
        private ConstraintLayout layout;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.categoryImage);
            name = itemView.findViewById(R.id.categoryName);
            layout = itemView.findViewById(R.id.categoryRecyclerLayout);
        }
    }
}
