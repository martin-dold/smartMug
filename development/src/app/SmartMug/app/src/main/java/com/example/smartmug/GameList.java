package com.example.smartmug;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

/*! @file  
 *  @brief This is the game list activity of the smartmug project.
 *
 *  @defgroup game_list Drinking game list
 *  @brief Lists the drinking games of the smartmug app.
 *
 *  @addtogroup java
 *  @{
 *
 *  @addtogroup game_list
 *  @{
 */

public class GameList extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_list);
    }

    /**
     * shows the game list
     * @param arg
     */
    public void onClick(View arg){
        switch (arg.getId()){
            case R.id.gameFiveSecButton:
                if(MainActivity.tcpClientRunning == true){
                    Intent intentGameFiveSeconds = new Intent (this, GameFiveSeconds.class);
                    startActivity(intentGameFiveSeconds);
                } else Toast.makeText(this, "You are not connected to any Mug", Toast.LENGTH_LONG).show();
                break;
            case R.id.gameGuessButton:
                if(MainActivity.tcpClientRunning == true){
                    Intent intentGameGuess = new Intent (this, GameGuessDrinking.class);
                    startActivity(intentGameGuess);
                } else Toast.makeText(this, "You are not connected to any Mug", Toast.LENGTH_LONG).show();
                break;


        }
    }
}

/*!
 * @}
 * @}
 */
