package com.example.smartmug;

import android.content.Intent;
import android.os.CountDownTimer;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.TextView;

import static com.example.smartmug.MainActivity.progressBar;

public class DrinkingGame extends AppCompatActivity {

    Button start,stop;
    Chronometer chrono;
    long time = 0;
    private TextView crono,mugBefore,mugAfter,total;
    int mugBef,mugAft;
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drinking_game);
        //chrono = (Chronometer) findViewById(R.id.Cronometro);
        start = (Button) findViewById(R.id.startButton);
       // stop = (Button) findViewById(R.id.stopButton);
        crono = (TextView) findViewById(R.id.cronometro) ;
        mugBefore = (TextView) findViewById(R.id.mugBeforeText) ;
        mugAfter = (TextView) findViewById(R.id.mugAfterText) ;
        start.setEnabled(true);
        //stop.setEnabled(false);
        mugBef = MugContent.mMugcontent_raw;
        total = (TextView) findViewById(R.id.totalText);
        //total.setText("Total drinked");
        mugBefore.setText("Mug Content: " + mugBef);
        mugAfter.setText("Mug Content: " + mugAft);

    }

    public void onClick(View arg){
        switch (arg.getId()){
            case R.id.startButton:
//                start.setEnabled(false);
//                stop.setEnabled(true);
//                chrono.setBase(SystemClock.elapsedRealtime());
//                chrono.start();

                new CountDownTimer(5000,1000) {
                    @Override
                    public void onTick(long millisUntilFinished) {
                       // chrono.setBase(millisUntilFinished);
                        crono.setText("seconds remaining: " + millisUntilFinished / 1000);
                    }

                    @Override
                    public void onFinish() {
                        crono.setText("done!");
                        mugAft = MugContent.mMugcontent_raw;
                        mugAfter.setText("Mug Content: " + mugAft);
                        int t = mugBef-mugAft;
                        total.setText("Total drinked: " +  t);
                        mugBef = 0;
                    }


                }.start();
                break;

//            case R.id.stopButton:
//                start.setEnabled(true);
//                stop.setEnabled(false);
//                chrono.stop();
//                break;

        }
    }
}
