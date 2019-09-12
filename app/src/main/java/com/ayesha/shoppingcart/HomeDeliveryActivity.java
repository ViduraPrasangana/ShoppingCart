package com.ayesha.shoppingcart;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.button.MaterialButton;
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
    private MaterialButton confirm;
    private MaterialButton cancel;
    private MaterialButton editAddress;
    private MaterialButton confirmAddress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_delivery);

        price = findViewById(R.id.homeDeliveryPrice);
        collectAddress = findViewById(R.id.collecAddress);
        collectAddressEdit = findViewById(R.id.collectAdrressEdit);
        confirm = findViewById(R.id.confirm);
        cancel = findViewById(R.id.cancel);
        editAddress = findViewById(R.id.editAddress);
        confirmAddress = findViewById(R.id.confirmAddress);

        price.setText(getIntent().getStringExtra("price"));
        this.setAddress();

        this.setEditAddressClickListner();
        this.setConfirmAddressClickListner();
        this.setCloseClickListner();

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

    private void setEditAddressClickListner(){

        this.editAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("setEditAddressClickLis", "12");
                editAddress.setVisibility(View.INVISIBLE);
                confirmAddress.setVisibility(View.VISIBLE);
                confirm.setEnabled(false);
                collectAddressEdit.setVisibility(View.VISIBLE);
                collectAddress.setVisibility(View.INVISIBLE);
            }
        });
    }

    private void setConfirmAddressClickListner(){

        this.confirmAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editAddress.setVisibility(View.VISIBLE);
                confirmAddress.setVisibility(View.INVISIBLE);
                confirm.setEnabled(true);
                collectAddressEdit.setVisibility(View.INVISIBLE);
                collectAddress.setText(HomeDeliveryActivity.this.collectAddressEdit.getText());
                collectAddress.setVisibility(View.VISIBLE);

            }
        });
    }

    private void setCloseClickListner(){
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
                        MainActivity.mainActivity.setPage(2);
                        finish();
                    }
                });

                AlertDialog dialog = builder.create();
                dialog.show();
                Button negativeButton = dialog.getButton(AlertDialog.BUTTON_NEGATIVE);
                negativeButton.setTextColor(Color.parseColor("#FFFF0400"));
                negativeButton.setBackgroundColor(Color.parseColor("#FFFCB9B7"));

            }
        });
    }
}
