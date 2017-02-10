package com.example.smartmug;

import android.content.Intent;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.util.Random;

/*! @file  
 *  @brief This is the guess drinking game activity of the smartmug project.
 *
 *  @defgroup game_guess Guess drinking game
 *  @brief Adds guess drinking game to the smartmug app.
 *
 *  @addtogroup java
 *  @{
 *
 *  @addtogroup game_guess
 *  @{
 */

public class GameGuessDrinking extends AppCompatActivity {

    private TextView randomNumber, mugBefore, result, chrono,ml;
    /**
     * mug content before and after drinking
     */
    int mugBef, mugAft;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_guess_drinking);
        randomNumber = (TextView) findViewById(R.id.txtRandNumb) ;
        ml = (TextView) findViewById(R.id.txtml) ;
        mugBefore = (TextView) findViewById(R.id.txtMugCant) ;
        mugBef = MugContent.mMugcontent_raw;
        mugBefore.setText(String.valueOf(mugBef));
        result = (TextView) findViewById(R.id.txtResultNumb) ;
        chrono = (TextView) findViewById(R.id.txtCron) ;
        mugAft = MugContent.mMugcontent_raw;
        //result.setText(String.valueOf(r));

       // mugAfter = (TextView) findViewById(R.id.mugAfterText) ;
    }

    public void onClick(View arg){
        switch (arg.getId()){
            case R.id.btnCreateNumber:
                int randomInt =  (int)Math.floor(Math.random()*(100-15+1)+15);
                if (randomInt > mugBef){
                    if (mugBef > 15) {
                        randomInt = (int)Math.floor(Math.random()*(mugBef-15+1)+15);
                    }else {
                        randomInt = (int)Math.floor(Math.random()*(mugBef));
                    }
                }
                randomNumber.setText(String.valueOf(randomInt));
                break;
            case R.id.btnStart:
                /**
                 * start the count down
                 */
                new CountDownTimer(10000,1000) {
                    @Override
                    public void onTick(long millisUntilFinished) {
                        // chrono.setBase(millisUntilFinished);
                        chrono.setText("seconds remaining: " + millisUntilFinished / 1000);
                    }
                    @Override
                    /**
                     * when it finish, show the mug content
                     */
                    public void onFinish() {
                        chrono.setText("seconds remaining: " + 0);
                        mugAft = MugContent.mMugcontent_raw;
                        int t = mugBef-mugAft;
                        result.setText(String.valueOf(t));
                        mugBef = mugAft;
                        mugBefore.setText(String.valueOf(mugBef));
                    }
                }.start();

        }
    }

}

/*!
 * @}
 * @}
 */
