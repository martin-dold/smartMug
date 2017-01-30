package com.example.smartmug;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

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

        TextView personalData = (TextView)findViewById(R.id.textViewPersonalData);
        if(PersonalData.height >= 0 && PersonalData.weight >=0){
            if(PersonalData.sex == false) {
                personalData.setText("Gender: Female"  + "  Height: " + PersonalData.height + "  Weight: " + PersonalData.weight);
            } else personalData.setText("Gender: Male"  + "  Height: " + PersonalData.height + "  Weight: " + PersonalData.weight);
        }
        TextView promileData = (TextView)findViewById(R.id.textViewPromile);
        if (promile <= 0.5){
            promileData.setText("Promile: "+promile);
        } else promileData.setText("Promile: "+promile +"  Don't drive anymore !!");

        TextView orderCount = (TextView)findViewById(R.id.textViewOrderCount);
        orderCount.setText("Order Count: ");

        TextView payAmount = (TextView)findViewById(R.id.textViewPayAmount);
        orderCount.setText("Pay Amount: ");

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
