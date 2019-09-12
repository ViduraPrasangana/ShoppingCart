package com.ayesha.shoppingcart;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.button.MaterialButton;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class RecyclerViewAdapterCart extends RecyclerView.Adapter<RecyclerViewAdapterCart.ViewHolder>{

    private ArrayList<CartItem> cartItems;
    private Context context;
    private int postiton;
    private OnItemClickListner clickListner;

    public interface OnItemClickListner{
        void onDeleteClick(int position);
    }

    public void setClickListner(OnItemClickListner clickListner1){
        clickListner = clickListner1;
    }

    public RecyclerViewAdapterCart(Context context,ArrayList<CartItem> cartItems) {
        this.cartItems = cartItems;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.holder_cart_product, parent, false);
        final ViewHolder holder = new ViewHolder(view);
//        holder.close.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                holder.cartHolderLayout.setVisibility(View.INVISIBLE);
//
//                DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference("carts/").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child(Integer.toString(cartItems.get(postiton).getId()));
//                dbRef.removeValue();
//
//                Constants.fetchCartItemsFromDB(view);
//            }
//        });
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        this.postiton = position;
        final CartItem tempCartItem = cartItems.get(position);
        final Product tempProduct = Constants.allProducts.get(tempCartItem.getId()-1); //This will hold the relevent Category object for the relevent Recyclcer View
        DisplayImageOptions options = new DisplayImageOptions.Builder().cacheOnDisk(true).build();
        ImageLoader imageLoader = ImageLoader.getInstance();
        imageLoader.displayImage(
                tempProduct.getImage_url(),
                holder.image, options);
        holder.name.setText(tempProduct.getName());
        holder.price.setText(Double.toString(tempProduct.getPrice()));
        holder.quantity.setText(Integer.toString(tempCartItem.getQuantity()));

    }

    @Override
    public int getItemCount() {
        return cartItems.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder{
        private CircleImageView image;
        private TextView name;
        private TextView price;
        private TextView quantity;
        private MaterialButton close;
        private ConstraintLayout cartHolderLayout;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.imageView);
            name = itemView.findViewById(R.id.name);
            price = itemView.findViewById(R.id.homeDeliveryPrice);
            quantity = itemView.findViewById(R.id.quantiity);
            close = itemView.findViewById(R.id.close);
            cartHolderLayout = itemView.findViewById(R.id.cartHolderLayout);

            close.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (clickListner != null){
                        int position = getAdapterPosition();
                        if(position != RecyclerView.NO_POSITION){
                            clickListner.onDeleteClick(position);
                        }
                    }

                }
            });


            name.setSelected(true);

        }
    }
}
