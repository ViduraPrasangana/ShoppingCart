package com.ayesha.shoppingcart;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class HolderBill extends AppCompatActivity {

    private String order_id;
    private TextView orderId, name, address, priceView, addressHeader;
    private MaterialButton homeButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        order_id = getIntent().getStringExtra("order_id");
        setContentView(R.layout.holder_bill);

        orderId = findViewById(R.id.order_id);
        name = findViewById(R.id.name);
        address = findViewById(R.id.address);
        priceView = findViewById(R.id.price);
        homeButton = findViewById(R.id.homeButton);
        addressHeader = findViewById(R.id.address_header);

        bindToView();

        setHomeButtonOnClickListner();
    }

    private void bindToView() {
        String tmpOrderId = ": " + order_id;
        orderId.setText(tmpOrderId);

        DatabaseReference ref = FirebaseDatabase.getInstance().
                getReference("orders")
                .child(FirebaseAuth.getInstance().getUid())
                .child(order_id);
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String orderType = dataSnapshot.child("orderType").getValue(String.class);
                if (orderType == null) return;
                if (orderType.equals(Constants.ORDERTYPE_DELIVERY)) {
                    HomeDeliveryOrder order = dataSnapshot.getValue(HomeDeliveryOrder.class);
                    String customer = ": " + order.getName();
                    String tmpAddress = ": "+order.getDeliveryAddress();
                    String price2 = ": " + order.getTotalPrice();
                    priceView.setText(price2);
                    name.setText(customer);
                    address.setText(tmpAddress);
                    addressHeader.setText("Delivery Address ");

                } else if (orderType.equals(Constants.ORDERTYPE_COLLECT)) {
                    CollectDeliveryOrder order = dataSnapshot.getValue(CollectDeliveryOrder.class);
                    String customer = ": " + order.getName();
                    String tmpAddress = ": "+order.getCollectAddress();
                    String price2 = ": " + order.getTotalPrice();
                    priceView.setText(price2);
                    name.setText(customer);
                    address.setText(tmpAddress);
                    addressHeader.setText("Collect Address ");

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    private void setHomeButtonOnClickListner() {
        this.homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity.mainActivity.setPage(0);
                finish();

            }
        });
    }

}
