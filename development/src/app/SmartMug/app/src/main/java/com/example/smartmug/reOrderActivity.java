package com.example.smartmug;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class reOrderActivity extends AppCompatActivity implements View.OnClickListener {


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

                /*

                if(MainActivity.tcpClientRunning == true){
                    //change the color
                    TCPClient.sendMessage("test\n");
                } else {

                    Context context = getApplicationContext();
                    CharSequence text = "No TCP Connection!";
                    int duration = Toast.LENGTH_LONG;

                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();
                }
*/

                break;
            case R.id.buttonLightWhite:

                break;
            case R.id.buttonLightGreen:

                break;
            case R.id.buttonLightRed:

                break;

        }
    }
}
