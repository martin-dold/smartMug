package com.example.smartmug;

import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

/*! @file  
 *  @brief This is the five seconds game activity of the smartmug project.
 *
 *  @defgroup game_5s 5 seconds game
 *  @brief Adds 5 second game to the smartmug app.
 *
 *  @addtogroup java
 *  @{
 *
 *  @addtogroup game_5s
 *  @{
 */

public class GameFiveSeconds extends AppCompatActivity {

    /**
     * Button for starting the chronometer
     */
    Button start;
    private TextView crono, mugBefore, mugAfter, total;
    /**
     * The content of mug before and after the chrono
     */
    int mugBef, mugAft;

    int[] mugValues = new int[5];
    int mugValuesIndex;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_five_seconds);
        start = (Button) findViewById(R.id.startButton);
        crono = (TextView) findViewById(R.id.cronometro);
        mugBefore = (TextView) findViewById(R.id.mugBeforeText);
        // mugAfter = (TextView) findViewById(R.id.mugAfterText) ;
        start.setEnabled(true);
        mugBef = MugContent.mMugcontent_raw;
        total = (TextView) findViewById(R.id.totalText);
        mugBefore.setText("Mug Content: " + mugBef);
        // mugAfter.setText("Mug Content: " + mugAft);
    }

    public void onClick(View arg) {
        switch (arg.getId()) {
            case R.id.startButton:
                /**
                 * start the count down
                 */
                new CountDownTimer(5000, 1000) {
                    @Override
                    public void onTick(long millisUntilFinished) {
                        // chrono.setBase(millisUntilFinished);
                        crono.setText("seconds remaining: " + millisUntilFinished / 1000);
                    }

                    @Override
                    /**
                     * when it finish, show the mug content
                     */
                    public void onFinish() {
                        crono.setText("Measuring done! Calculating result.");
                    }
                }.start();
                new CountDownTimer(9000, 1000) {
                    @Override
                    public void onTick(long millisUntilFinished) {

                    }

                    @Override
                    /**
                     * when it finish, show the mug content
                     */
                    public void onFinish() {
                        crono.setText("done!");
                        mugAft = MugContent.mMugcontent_raw;
                        //mugAfter.setText("Mug Content: " + mugAft);
                        int t = mugBef - mugAft;
                        total.setText("Total drinked: " + t);
                        mugBef = mugAft;
                        mugBefore.setText("Mug Content: " + mugBef);
                    }
                }.start();
                break;


        }
    }
}

/*!
 * @}
 * @}
 */
