package com.example.smartmug;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;

/*! @file  
 *  @brief This is the statistics activity of the smartmug project.
 *
 *  @defgroup statistics Statistics
 *  @brief Contains the statistics of the user of the smartmug app.
 *
 *  @addtogroup java
 *  @{
 *
 *  @addtogroup statistics
 *  @{
 */

public class Statistic extends AppCompatActivity {

    private EditText numOrder;

    public static double promile =0;

    public static int drinkingVolume=0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistic);

    }


    public static double promileCounter(int drinkingVolume){
        double a;
        a = drinkingVolume * 0.05 * 0.8;
        if(PersonalData.sex == true){
            promile = a / (0.7*PersonalData.weight);
        } else if(PersonalData.sex == false){
            double helper = 0.6*PersonalData.weight;
            promile = a/helper;
        }else {promile = 0;}

        return promile;
    }

    public static void addDrinkingVolume(int addAmount){
        drinkingVolume = drinkingVolume + addAmount;
        promileCounter(drinkingVolume);
    }

}

/*!
 * @}
 * @}
 */
