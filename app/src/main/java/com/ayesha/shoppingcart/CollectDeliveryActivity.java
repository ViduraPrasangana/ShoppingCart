package com.ayesha.shoppingcart;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class CollectDeliveryActivity extends AppCompatActivity {

    private TextView priceView;
    private TextView collectDate;
    private MaterialButton confirm;
    private MaterialButton close;
    private double price;

    int collectTime;
    String address;
    final Calendar cal = Calendar.getInstance();
    SimpleDateFormat format1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.collect_delivery);

        priceView = findViewById(R.id.homeDeliveryPrice);
        price = getIntent().getDoubleExtra("price",0);
        priceView.setText(String.valueOf(price));

        collectDate = findViewById(R.id.collectDate);
        confirm = findViewById(R.id.confirm);
        close = findViewById(R.id.cancel);

        DatabaseReference ref = FirebaseDatabase.getInstance()
                .getReference("company");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                collectTime = dataSnapshot.child("collect_time_days").getValue(Integer.class);
                cal.add(Calendar.DATE, +collectTime); //will collect the date after a week
                format1 = new SimpleDateFormat("yyyy-MM-dd HH:00:00");
                collectDate.setText(""+format1.format(cal.getTime()));

                address = dataSnapshot.child("pick_up_place").getValue(String.class);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        this.setCloseClickListner();
        this.setConfirmClickListner();

    }

    private void setConfirmClickListner(){
        this.confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(CollectDeliveryActivity.this);
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

        final CollectDeliveryOrder order = new CollectDeliveryOrder(
                Constants.cartItems, price,
                ""+format1.format(cal.getTime()),
                address,
                Constants.user.getFirstName() + " " + Constants.user.getLastName(),
                Constants.user.getNumber());
        DatabaseReference ref = FirebaseDatabase.getInstance()
                .getReference("orders/")
                .child(FirebaseAuth.getInstance().getUid())
                .child(order.getOrderId());
        ref.setValue(order).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Intent intent = new Intent(CollectDeliveryActivity.this, HolderBill.class);
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


    private void setCloseClickListner(){
        this.close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(CollectDeliveryActivity.this);
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
                        MainActivity.mainActivity.setPage(2);
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
