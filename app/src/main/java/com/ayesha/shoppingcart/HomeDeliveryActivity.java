package com.ayesha.shoppingcart;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class HomeDeliveryActivity extends AppCompatActivity {

    private TextView priceText;
    private TextView deliveryAddress;
    private TextInputEditText collectAddressEdit;
    private MaterialButton confirm;
    private MaterialButton cancel;
    private MaterialButton editAddress;
    private MaterialButton confirmAddress;

    private double price;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_delivery);

        priceText = findViewById(R.id.homeDeliveryPrice);
        deliveryAddress = findViewById(R.id.deliveryAddress);
        collectAddressEdit = findViewById(R.id.collectAdrressEdit);
        confirm = findViewById(R.id.confirm);
        cancel = findViewById(R.id.cancel);
        editAddress = findViewById(R.id.editAddress);
        confirmAddress = findViewById(R.id.confirmAddress);

        price = getIntent().getDoubleExtra("price", 0);
        priceText.setText(String.valueOf(price));
        this.setAddress();

        this.setEditAddressClickListner();
        this.setConfirmAddressClickListner();
        this.setCloseClickListner();

        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(HomeDeliveryActivity.this);
                builder.setTitle("Are You Sure?");
                builder.setMessage("Are you sure you want to make the Order?");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        makeOrder();
                    }
                });

                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });

                AlertDialog dialog = builder.create();
                dialog.show();
                Button negativeButton = dialog.getButton(AlertDialog.BUTTON_NEGATIVE);
                negativeButton.setTextColor(Color.parseColor("#FFFF0400"));
                negativeButton.setBackgroundColor(Color.parseColor("#ffffff"));


            }
        });

    }

    private void makeOrder(){
        final HomeDeliveryOrder order = new HomeDeliveryOrder(
                Constants.cartItems, price,
                deliveryAddress.getText().toString(),
                Constants.user.getFirstName() + " " + Constants.user.getLastName(),
                Constants.user.getNumber());
        DatabaseReference ref = FirebaseDatabase.getInstance()
                .getReference("orders/")
                .child(FirebaseAuth.getInstance().getUid())
                .child(order.getOrderId());
        ref.setValue(order).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Intent intent = new Intent(HomeDeliveryActivity.this, HolderBill.class);
                intent.putExtra("order_id",order.getOrderId());
                startActivity(intent);
            }
        });
        FirebaseDatabase.getInstance()
                .getReference("carts/")
                .child(FirebaseAuth.getInstance().getUid())
                .removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                finish();
            }
        });
    }

    private void setAddress() {
        DatabaseReference userRef = FirebaseDatabase.getInstance()
                .getReference("users/")
                .child(FirebaseAuth.getInstance().getUid()).child("address");
        userRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                deliveryAddress.setText(dataSnapshot.getValue(String.class));
                collectAddressEdit.setText(dataSnapshot.getValue(String.class));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void setEditAddressClickListner() {

        this.editAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("setEditAddressClickLis", "12");
                editAddress.setVisibility(View.INVISIBLE);
                confirmAddress.setVisibility(View.VISIBLE);
                confirm.setEnabled(false);
                collectAddressEdit.setVisibility(View.VISIBLE);
                deliveryAddress.setVisibility(View.INVISIBLE);
            }
        });
    }

    private void setConfirmAddressClickListner() {

        this.confirmAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editAddress.setVisibility(View.VISIBLE);
                confirmAddress.setVisibility(View.INVISIBLE);
                confirm.setEnabled(true);
                collectAddressEdit.setVisibility(View.INVISIBLE);
                deliveryAddress.setVisibility(View.VISIBLE);

                FirebaseDatabase.getInstance()
                        .getReference("users/" + FirebaseAuth.getInstance().getUid() + "/address/")
                        .setValue(collectAddressEdit.getText().toString());

            }
        });
    }

    private void setCloseClickListner() {
        this.cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(HomeDeliveryActivity.this);
                builder.setTitle("Are You Sure?");
                builder.setMessage("Are you sure you want to cancel the Order?");
                builder.setPositiveButton("NO", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });

                builder.setNegativeButton("YES", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        finish();
                    }
                });

                AlertDialog dialog = builder.create();
                dialog.show();
                Button negativeButton = dialog.getButton(AlertDialog.BUTTON_NEGATIVE);
                negativeButton.setTextColor(Color.parseColor("#FFFF0400"));
                negativeButton.setBackgroundColor(Color.parseColor("#ffffff"));

            }
        });
    }
}
