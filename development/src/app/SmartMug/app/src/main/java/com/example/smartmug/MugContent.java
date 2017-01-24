package com.example.smartmug;

import android.app.Activity;
import android.util.Log;

import static com.example.smartmug.MainActivity.mBuilder;
import static com.example.smartmug.MainActivity.notificationmanager;
import static com.example.smartmug.MainActivity.progressBar;

/**
 * Created by FuechsleXD on 06.12.2016.
 */

public class MugContent {

    /** @brief Holds the last received content level of the mug in percent compared
     *  to a mug with max. 400g, i.e. 400g equals 100%. */
    public static int mMugcontent_percent;
    /** @brief Holds the last received content level of the mug in unit 'g' (gramm). */
    public static int mMugcontent_raw;

    public static void setMugContent(int tag, int len, byte[] value) {

        switch (tag)
        {
            /* TAG = 1: Sensor Data */
            case 1:
                /* The tag "sensor data" should have a value length of 2. */
                if(len == 2)
                {
                    /* Convert the two bytes into one value. */
                    mMugcontent_raw = (((value[0] & 0xff) << 8) | (value[1] & 0xff));
                    mMugcontent_percent = mMugcontent_raw / 4;

                    Log.d("MugContent", "_raw = " + Integer.toString(mMugcontent_raw));
                    Log.d("MugContent", "_percent = " + String.valueOf(mMugcontent_percent));

                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            progressBar.post(new Runnable() {
                                public void run() {
                                    progressBar.setProgress(mMugcontent_percent);
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
