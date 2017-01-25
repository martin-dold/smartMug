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

    private TextView randomNumber, mugBefore, result, chrono;
    /**
     * mug content before and after drinking
     */
    int mugBef, mugAft;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_guess_drinking);
        randomNumber = (TextView) findViewById(R.id.txtRandNumb) ;
        mugBefore = (TextView) findViewById(R.id.txtMugCant) ;
        mugBef = MugContent.mMugcontent_raw;
        mugBefore.setText(String.valueOf(mugBef));
        result = (TextView) findViewById(R.id.txtResultNumb) ;
        chrono = (TextView) findViewById(R.id.txtChrono) ;
        mugAft = MugContent.mMugcontent_raw;
        //result.setText(String.valueOf(r));

       // mugAfter = (TextView) findViewById(R.id.mugAfterText) ;
    }

    public void onClick(View arg){
        switch (arg.getId()){
            case R.id.btnCreateNumber:
                Random randomGenerator = new Random();
                int randomInt = randomGenerator.nextInt(100);
                randomNumber.setText(String.valueOf(randomInt));
                break;
            case R.id.startButton:
                /**
                 * start the count down
                 */
                new CountDownTimer(5000,1000) {
                    @Override
                    public void onTick(long millisUntilFinished) {
                        // chrono.setBase(millisUntilFinished);
                        chrono.setText("" + millisUntilFinished / 1000);
                    }
                    @Override
                    /**
                     * when it finish, show the mug content
                     */
                    public void onFinish() {
                        //crono.setText("done!");
                        mugAft = MugContent.mMugcontent_raw;
                        int t = mugBef-mugAft;
                        result.setText(String.valueOf(t));
                        mugBef = 0;
                    }
                }.start();

        }
    }

}

/*!
 * @}
 * @}
 */
