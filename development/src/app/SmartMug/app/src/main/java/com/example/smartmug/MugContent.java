package com.example.smartmug;

/**
 * Created by FuechsleXD on 06.12.2016.
 */

public class MugContent {

    public static int Mugcontent;

    public static void setMugContent(byte[] bytearray){
        Mugcontent = ((int) bytearray[0]);
        MainActivity.setProgressValue(Mugcontent);
    }
}
