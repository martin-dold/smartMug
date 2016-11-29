package com.example.smartmug;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class Order extends AppCompatActivity {

    private TextView tvOrder;
    public int orderCont = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        tvOrder=(TextView)findViewById(R.id.txtOrder);
    }

    public void onClick(View arg){
        switch (arg.getId()){
            case R.id.btnOrder:
                orderCont++;
                String resu = String.valueOf(orderCont);
                tvOrder.setText(resu);
                break;
        }
    }


}
