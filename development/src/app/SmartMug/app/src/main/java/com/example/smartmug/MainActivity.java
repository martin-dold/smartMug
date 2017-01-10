package com.example.smartmug;


import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.NotificationCompat;
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
    public static TextView tvOrder;
    public static ProgressBar progressBar;
    int progress = 0;

    public static boolean tcpClientRunning = false;


    //Notification
    public static NotificationCompat.Builder mBuilder;
    private static int finalid = 3333;
    public static NotificationManager notificationmanager;

    public static int mugContent = 0;



    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tvOrder = (TextView) findViewById(R.id.txtOrder);
        View btnManuellInput = findViewById(R.id.connectMugButton);
        btnManuellInput.setOnClickListener(this);
        View btnOrder = findViewById(R.id.orderButton);
        btnOrder.setOnClickListener(this);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);

        //Notification
        mBuilder = new NotificationCompat.Builder(this);
        mBuilder.setAutoCancel(true);
        notificationmanager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        changeNotificationContent();
        showNotification();

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

                Intent intentOrder = new Intent(this, reOrderActivity.class);
                startActivity(intentOrder);
                break;
            case R.id.personalDataButton:

                Intent intentPersData = new Intent (this, PersonalData.class);
                startActivity(intentPersData);
                break;
            case R.id.gameButton:
                Intent intentGameList = new Intent (this, GameList.class);
                startActivity(intentGameList);
                break;


        }
    }

    private void showNotification() {

        notificationmanager.notify(finalid,mBuilder.build());

    }

    private void changeNotificationContent(){
        mBuilder.setSmallIcon(R.drawable.ic_launcher)
                .setContentTitle("Smart Mug")
                .setContentText(mugContent +"%")
                .setTicker("This is the Ticker");
        Intent intent = new Intent(this,MainActivity.class);

        PendingIntent pendingIntent = PendingIntent.getActivity(this,0, intent,PendingIntent.FLAG_UPDATE_CURRENT);

        mBuilder.setContentIntent(pendingIntent);
    }

    public static void setMugContent(int value){
        mugContent = value;
    }
    public static int getFinalid() {
        return finalid;
    }
}
