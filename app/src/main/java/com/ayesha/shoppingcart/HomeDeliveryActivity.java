package com.ayesha.shoppingcart;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.google.android.material.picker.MaterialTextInputPicker;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textview.MaterialTextView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class HomeDeliveryActivity extends AppCompatActivity {

    private TextView price;
    private TextView collectAddress;
    private TextInputEditText collectAddressEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_delivery);

        price = findViewById(R.id.homeDeliveryPrice);
        collectAddress = findViewById(R.id.collecAddress);
        collectAddressEdit = findViewById(R.id.collectAdrressEdit);

        price.setText(getIntent().getStringExtra("price"));
        this.setAddress();

    }

    private void setAddress(){
        DatabaseReference userRef = FirebaseDatabase.getInstance().getReference("users/"+ FirebaseAuth.getInstance().getUid() + "/");
        userRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot: dataSnapshot.getChildren()) {
                    collectAddress.setText(snapshot.getValue(String.class));
                    collectAddressEdit.setText(snapshot.getValue(String.class));
                    break;
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
