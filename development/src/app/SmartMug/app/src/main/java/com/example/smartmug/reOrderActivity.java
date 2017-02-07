package com.example.smartmug;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

/*! @file  
 *  @brief This is the re order activity of the smartmug project.
 *
 *  @defgroup reorder Reorder
 *  @brief Allows to re order a drink using the smartmug app.
 *
 *  @addtogroup java
 *  @{
 *
 *  @addtogroup reorder
 *  @{
 */

public class reOrderActivity extends AppCompatActivity implements View.OnClickListener {

    public static int countOrders;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_re_order);

        View btnBlue = findViewById(R.id.buttonLightBlue);
        btnBlue.setOnClickListener(this);
        View btnWhite = findViewById(R.id.buttonLightWhite);
        btnWhite.setOnClickListener(this);
        View btnRed = findViewById(R.id.buttonLightRed);
        btnRed.setOnClickListener(this);
        View btnGreen = findViewById(R.id.buttonLightGreen);
        btnGreen.setOnClickListener(this);
    }

    @Override
    public void onClick(View arg) {

        switch (arg.getId()) {
            case R.id.buttonLightBlue:

                byte[] blue = new byte[] {0x02,0x01,0x03,0x0A};
                changeColor(blue);


                break;
            case R.id.buttonLightWhite:

                byte[] white = new byte[] {0x02,0x01,0x04,0x0A};
                changeColor(white);


                break;
            case R.id.buttonLightGreen:

                byte[] green = new byte[] {0x02,0x01,0x02,0x0A};
                changeColor(green);

                break;
            case R.id.buttonLightRed:

                byte[] red = new byte[] {0x02,0x01,0x01,0x0A};
                changeColor(red);
                countOrders++;

                break;

        }
    }

    private void changeColor(byte[] color) {

        if(MainActivity.tcpClientRunning == true){
            //change the color
            TCPClient.sendMessageByteArray(color);
        } else {

            Context context = getApplicationContext();
            CharSequence text = "No TCP Connection!";
            int duration = Toast.LENGTH_LONG;

            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
        }
    }
}

/*!
 * @}
 * @}
 */
