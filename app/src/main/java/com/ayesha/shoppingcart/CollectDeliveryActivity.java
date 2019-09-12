package com.ayesha.shoppingcart;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.button.MaterialButton;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class CollectDeliveryActivity extends AppCompatActivity {

    private TextView price;
    private TextView collectDate;
    private MaterialButton confirm;
    private MaterialButton close;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.collect_delivery);

        price = findViewById(R.id.homeDeliveryPrice);
        price.setText(getIntent().getStringExtra("price"));

        collectDate = findViewById(R.id.collectDate);
        confirm = findViewById(R.id.confirm);
        close = findViewById(R.id.cancel);

        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, +7); //will collect the date after a week
        SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd HH:00:00");

        collectDate.setText(""+format1.format(cal.getTime()));

        this.setCloseClickListner();
        this.setConfirmClickListner();

    }

    private void setConfirmClickListner(){
        this.confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

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
                negativeButton.setBackgroundColor(Color.parseColor("#FFFCB9B7"));

            }
        });
    }


}
