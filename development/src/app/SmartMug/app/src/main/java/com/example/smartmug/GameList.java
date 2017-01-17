package com.example.smartmug;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

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
            case R.id.gameOneButton:
                Intent intentGameOne = new Intent (this, DrinkingGame.class);
                startActivity(intentGameOne);
                break;

        }
    }
}
