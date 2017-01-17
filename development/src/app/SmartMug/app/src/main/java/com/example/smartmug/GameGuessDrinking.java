package com.example.smartmug;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.util.Random;

public class GameGuessDrinking extends AppCompatActivity {

    private TextView randomNumber, mugBefore,mugAfter;
    /**
     * mug content before and after drinking
     */
    int mugBef, mugAfter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_guess_drinking);
        randomNumber = (TextView) findViewById(R.id.txtRandNumb) ;
        mugBefore = (TextView) findViewById(R.id.txtMugCant) ;
        mugBef = MugContent.mMugcontent_raw;
        //total = (TextView) findViewById(R.id.totalText);
        mugBefore.setText(mugBef);

       // mugAfter = (TextView) findViewById(R.id.mugAfterText) ;
    }

    public void onClick(View arg){
        switch (arg.getId()){
            case R.id.btnCreateNumber:
                Random randomGenerator = new Random();
                int randomInt = randomGenerator.nextInt(100);
                randomNumber.setText(String.valueOf(randomInt));
                break;

        }
    }

}
