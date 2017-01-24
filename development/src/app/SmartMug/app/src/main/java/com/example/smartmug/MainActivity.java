package com.example.smartmug;


import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
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

/*! @file  
 *  @brief This is the main activity of the smartmug project.
 *
 *  @defgroup java Android Code
 *  @brief Android code of the smartmug firmware.
 *
 *  @defgroup main SmartMug Main
 *  @brief Main acitivity of the smartmug app.
 *
 *  @addtogroup java
 *  @{
 *
 *  @addtogroup main
 *  @{
 */



public class MainActivity extends AppCompatActivity implements OnClickListener {

    /**
     * Value with the re-order count (how many beers did I drunk up to now)
     */
    public int orderCount = 0;
    /**
     *
     */
    public static TextView tvOrder;
    /**
     * Declare the ProgressBar
     */
    public static ProgressBar progressBar;

    /**
     * value whether tcp connection is true or false
     */
    public static boolean tcpClientRunning = false;


    //Notification
    /**
     * Notification Builder
     */
    public static NotificationCompat.Builder mBuilder;
    /**
     * id of the Notification
     */
    private static int finalid = 3333;
    /**
     * Notification Manager can be use to change the Notification
     */
    public static NotificationManager notificationmanager;

    /**
     * Helper class instance for Network Service Discovery (NSD) using mDNS
     */
    public NsdHelper mNsdHelper;

    /**
     * create the Activity
     * @param savedInstanceState
     */
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tvOrder = (TextView) findViewById(R.id.txtOrder);

        /**
         * Create ConnectMugButton
         */
        View btnManuellInput = findViewById(R.id.connectMugButton);
        /**
         * set the ClickOnListener for ConnectMugButton
         */
        btnManuellInput.setOnClickListener(this);
        /**
         * Create orderButton
         */
        View btnOrder = findViewById(R.id.orderButton);
        /**
         * set the ClickOnListener for orderButton
         */
        btnOrder.setOnClickListener(this);
        /**
         * build new ProgressBar
         */
        progressBar = (ProgressBar) findViewById(R.id.progressBar);

        //Notification
        /**
         * build new Notification
         */
        mBuilder = new NotificationCompat.Builder(this);
        /**
         * set it that cancel is possible
         */
        mBuilder.setAutoCancel(true);
        /**
         * create the NotificationManager
         */
        notificationmanager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        /**
         * method for changing the NotificationContent for the first initiation
         */
        changeNotificationContent();
        /**
         * method for displaying the Notification
         */
        showNotification();

        mNsdHelper = new NsdHelper(this);
        mNsdHelper.discoverServices();
    }

    /**
     * Method for which Button is pressed
     * @param arg
     */
    public void onClick(View arg){
        switch (arg.getId()){
            case R.id.connectMugButton:
                Intent intentConect = new Intent (this, ConnectionActivity.class);
                startActivity(intentConect);
                break;
            case R.id.statisticButon:
                Intent intentStat = new Intent (this, Statistic.class);
                intentStat.putExtra("orderCount", orderCount);
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

    /**
     * display the Notification
     */
    private void showNotification() {

        notificationmanager.notify(finalid,mBuilder.build());

    }

    /**
     * change the Notfication Content
     */
    private void changeNotificationContent(){
        mBuilder.setSmallIcon(R.drawable.ic_launcher)
                /* set the Title of the Notification */
                .setContentTitle("Smart Mug")
                /* set the Notification Content*/
                .setContentText(0 +"%")
                /* set the ticker of the Notification*/
                .setTicker("This is the Ticker");
        Intent intent = new Intent(this,MainActivity.class);

        PendingIntent pendingIntent = PendingIntent.getActivity(this,0, intent,PendingIntent.FLAG_UPDATE_CURRENT);

        mBuilder.setContentIntent(pendingIntent);
    }

    /**
     * return the final id from the Notification
     * @return
     */
    public static int getFinalid() {
        return finalid;
    }
}

/*!
 * @}
 * @}
 */
