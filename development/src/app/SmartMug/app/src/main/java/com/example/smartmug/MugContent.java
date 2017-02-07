package com.example.smartmug;

import android.app.Activity;
import android.util.Log;

import static com.example.smartmug.MainActivity.mBuilder;
import static com.example.smartmug.MainActivity.notificationmanager;
import static com.example.smartmug.MainActivity.progressBar;

/*! @file  
 *  @brief This is the mug content class of the smartmug project.
 *
 *  @defgroup mugcontent Drinking game list
 *  @brief Lists the drinking games of the smartmug app.
 *
 *  @addtogroup java
 *  @{
 *
 *  @addtogroup mugcontent
 *  @{
 */

public class MugContent {

    /** @brief Holds the last received content level of the mug in percent compared
     *  to a mug with max. 400g, i.e. 400g equals 100%. */
    public static int mMugcontent_percent;
    /** @brief Holds the last received content level of the mug in unit 'g' (gramm). */
    public static int mMugcontent_raw = 0;
    /** @brief Difference between current _raw and previous _raw. */
    public static int mMugcontent_diff_to_last;
    /** @brief Old value of the MugCcontent*/
    public static int tempOldValue = 0;

    public static final int maxLiquidLevel = 300 ;

    public static void setMugContent(int tag, int len, byte[] value) {

        switch (tag)
        {
            /* TAG = 1: Sensor Data */
            case 1:
                /* The tag "sensor data" should have a value length of 2. */
                if(len == 2)
                {

                    tempOldValue = mMugcontent_raw;
                    /* Convert the two bytes into one value. update the new Mugcontent*/
                    mMugcontent_raw = (((value[0] & 0xff) << 8) | (value[1] & 0xff));



                        mMugcontent_diff_to_last = tempOldValue - mMugcontent_raw;

                        mMugcontent_percent = (mMugcontent_raw*100)/maxLiquidLevel;


                        Log.d("MugContent", "_raw = " + Integer.toString(mMugcontent_raw));
                        Log.d("MugContent", "_percent = " + String.valueOf(mMugcontent_percent));


                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            progressBar.post(new Runnable() {
                                public void run() {
                                    progressBar.setProgress(mMugcontent_percent);
                                    //Update Notification
                                    //mBuilder.setContentText(mMugcontent_percent +"%");
                                    //notificationmanager.notify(MainActivity.getFinalid(),mBuilder.build());

                                    /**Update drink Amount */
                                    if(mMugcontent_diff_to_last >=0){
                                        Statistic.addDrinkingVolume(mMugcontent_diff_to_last);
                                    }

                                    /**Update Notification */
                                    if(Statistic.promile >= 0.5){
                                        //Update Notification
                                        mBuilder.setContentText(mMugcontent_percent +"%" + "\n" +"    Promile : " + String.format("%.2f", Statistic.promile));
                                        notificationmanager.notify(MainActivity.getFinalid(),mBuilder.build());
                                    } else
                                        //Update Notification
                                        mBuilder.setContentText(mMugcontent_percent +"%");
                                    notificationmanager.notify(MainActivity.getFinalid(),mBuilder.build());

                                }
                            });
                        }
                    }).start();
                }
        }
    }
}

/*!
 * @}
 * @}
 */
