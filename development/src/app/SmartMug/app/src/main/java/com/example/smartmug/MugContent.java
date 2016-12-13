package com.example.smartmug;

import android.app.Activity;
import android.util.Log;

import static com.example.smartmug.MainActivity.progressBar;

/**
 * Created by FuechsleXD on 06.12.2016.
 */

public class MugContent {

    public static int Mugcontent;
    private static Activity activity;

    public static void setMugContent(byte[] bytearray) {
        Byte first = bytearray[2];
        Byte second = bytearray[3];
        if (first != null) {
            if (second != null) {
                int val = ((first & 0xff) << 8) | (second & 0xff);
                Log.e("Val: ", String.valueOf(val));
                Mugcontent = (val / 400) * 100;


                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        progressBar.post(new Runnable() {
                            public void run() {
                                progressBar.setProgress(Mugcontent);
                            }
                        });
                    }
                }).start();


            }
        }

        /*
        new Thread(new Runnable() {
            @Override
            public void run() {
                runOnUiThread(new Runnable()){
                    public void run(){
                        MainActivity.setProgressTest(Mugcontent);
                    }
                }
            }
        }).start();
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                MainActivity.setProgressTest(Mugcontent);
            }
        });


    } */


    }
}
