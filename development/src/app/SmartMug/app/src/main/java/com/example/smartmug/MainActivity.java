package com.example.smartmug;


import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity implements OnClickListener {

    public int orderCont = 0;
    private TextView tvOrder;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tvOrder=(TextView)findViewById(R.id.txtOrder);
        View btnManuellInput = findViewById(R.id.connectMugButton);
        btnManuellInput.setOnClickListener(this);
    }

    public void onClick(View arg){
        switch (arg.getId()){
            case R.id.connectMugButton:
                Intent intentConect = new Intent (this, ConnectionActivity.class);
                startActivity(intentConect);
                break;
            case R.id.statisticButon:
                Intent intentStat = new Intent (this, Statistic.class);
                intentStat.putExtra("orderCount", orderCont);
                startActivity(intentStat);
                break;
            case R.id.orderButton:
                orderCont++;
                String resu = String.valueOf(orderCont);
                tvOrder.setText(resu);
//                Intent intentOrder = new Intent (this, Statistic.class);
//                startActivity(intentOrder);
                break;
            case R.id.personalDataButton:
                Intent intentPersData = new Intent (this, PersonalData.class);
                startActivity(intentPersData);
                break;

        }
    }

}
