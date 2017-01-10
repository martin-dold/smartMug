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

    public static int Mugcontent_percent;
    public static int Mugcontent_raw;

    public static void setMugContent(byte[] bytearray) {
        Byte first = bytearray[2];
        Byte second = bytearray[3];
        if (first != null) {
            if (second != null) {
                int Mugcontent_raw = ((first & 0xff) << 8) | (second & 0xff);
                Log.e("Value get from mug: ", String.valueOf(Mugcontent_raw));

                //int fillingWeight = (val);
                //Mugcontent = (100/400) * val;
                Mugcontent_percent = Mugcontent_raw/4;
                MainActivity.setMugContent(Mugcontent_percent);

                //Mugcontent = 50;
                Log.e("Value of Mugcontent: ", String.valueOf(Mugcontent_percent));


                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        progressBar.post(new Runnable() {
                            public void run() {
                                progressBar.setProgress(Mugcontent_percent);
                                //Update Notification
                                mBuilder.setContentText(Mugcontent_percent +"%");
                                notificationmanager.notify(MainActivity.getFinalid(),mBuilder.build());
                            }
                        });
                    }
                }).start();


            }
        }

    }
}
