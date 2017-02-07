package com.example.smartmug;

import android.content.Context;
import android.content.SharedPreferences;
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

    public String height, weight;

    public SharedPreferences p;

    public static int allTheTimeDrinkingAmount;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistic);

        p = getSharedPreferences("personalData", Context.MODE_PRIVATE);

        height = p.getString("height","");
        weight = p.getString("weight","");

        TextView personalData = (TextView)findViewById(R.id.textViewPersonalData);
        if(PersonalData.height >= 0 && PersonalData.weight >=0){
//           /* if(PersonalData.sex == false) {
//                personalData.setText("Gender: Female"  + "  Height: " + PersonalData.height + "  Weight: " + PersonalData.weight);
//            } else personalData.setText("Gender: Male"  + "  Height: " + PersonalData.height + "  Weight: " + PersonalData.weight);*/
            if(PersonalData.sex == false) {
                 personalData.setText("Gender: Female"  + "  Height: " + height + "  Weight: " + weight);
            } else personalData.setText("Gender: Male"  + "  Height: " + height + "  Weight: " + weight);
        }
        TextView promileData = (TextView)findViewById(R.id.textViewPromile);
        if (promile <= 0.5){
            promileData.setText("Promile: "+  String.format("%.2f", promile));
        } else promileData.setText("Promile: "+ String.format("%.2f", promile) +"  Don't drive anymore !!");

        TextView orderCount = (TextView)findViewById(R.id.textViewOrderCount);
        orderCount.setText("Order Count: " + reOrderActivity.countOrders);

        TextView payAmount = (TextView)findViewById(R.id.textViewPayAmount);
        payAmount.setText("Pay Amount: " + (3*reOrderActivity.countOrders)+ "€");

        TextView timeDrinking = (TextView)findViewById(R.id.textViewTimeDrinking);
        timeDrinking.setText("Time Drinking: " + allTheTimeDrinkingAmount);

    }


    public static double promileCounter(int drinkingVolume){
        double a;
        a = drinkingVolume * 0.05 * 0.8;
        if(PersonalData.weight > 0)
        {
            if(PersonalData.sex == true){
                promile = a / (0.7*PersonalData.weight);
            } else if(PersonalData.sex == false){
                double helper = 0.6*PersonalData.weight;
                promile = a/helper;
            }else {promile = 0;}
        }
        else
        {
            promile = 0;
        }
        return promile;
    }

    public static void addDrinkingVolume(int addAmount){
        drinkingVolume = drinkingVolume + addAmount;
        promileCounter(drinkingVolume);
        allTheTimeDrinkingAmount = allTheTimeDrinkingAmount + addAmount;
    }

}

/*!
 * @}
 * @}
 */
