package com.ayesha.shoppingcart;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
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

        price = findViewById(R.id.price);
        price.setText(getIntent().getStringExtra("price"));

        collectDate = findViewById(R.id.collectDate);

        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, +7);
        SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd HH:00:00");

        collectDate.setText(""+format1.format(cal.getTime()));

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
                
            }
        });
    }


}
