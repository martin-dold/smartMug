package com.example.smartmug;


import android.content.Intent;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import static android.R.id.progress;


public class MainActivity extends AppCompatActivity implements OnClickListener {

    public int orderCont = 0;
    private TextView tvOrder;
   // private TCPClient tcpC = new TCPClient();
    public static ProgressBar progressBar;
    int progress = 0;



    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tvOrder=(TextView)findViewById(R.id.txtOrder);
        View btnManuellInput = findViewById(R.id.connectMugButton);
        btnManuellInput.setOnClickListener(this);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);

    }

    public static void setProgressValue(final int progress) {

        // set the progress
        progressBar.setProgress(progress);
        // thread is used to change the progress value
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(2500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                setProgressValue(progress + 10);
            }
        });
        thread.start();
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
                setProgressValue(progress);
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
