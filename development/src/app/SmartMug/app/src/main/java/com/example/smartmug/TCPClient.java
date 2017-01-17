package com.example.smartmug;

import android.location.OnNmeaMessageListener;
import android.util.Log;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.*;
import java.net.Socket;
import java.lang.System;
import java.nio.ByteBuffer;

/**
 * Created by FuechsleXD on 04.12.2016.
 */

public class TCPClient {

    /** while this is true, the server will continue running */
    private boolean mRun = false;
    /** used to send messages */
    private static DataOutputStream mByteOutputStream;
    /** used to read messages from the server */
    private BufferedInputStream inputS;
    /** Rx data buffer to read single bytes of data from the socket stream. */
    private byte[] rxData = new byte[5];
    /** Number of bytes read in a single socket.read() instruction. */
    private int rxDataLen = 0;
    /** Total number of bytes read. */
    private int rxDataLenTotal = 0;

    /**
     * Constructor of the class. OnMessagedReceived listens for the messages received from server
     */
    public TCPClient(OnMessageReceived listener) {

    }

    /**
     * Sends the message (as byte array) entered by client to the server
     *
     * @param array text entered by client
     */
    public static void sendMessageByteArray(byte[] array){

        if (mByteOutputStream != null) {
            try {
                mByteOutputStream.write(array);
                mByteOutputStream.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Close the connection and release the members
     */
    public void stopClient(){
        mRun = false;

        if(mByteOutputStream != null){
            try {
                mByteOutputStream.flush();
                mByteOutputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        /* Reset all private variables when disconnecting. */
        mByteOutputStream = null;
        inputS = null;
        rxDataLen = 0;
        rxDataLenTotal = 0;

    }

    public void run(String ip, int port) {

        mRun = true;

        try {
            //here you must put your computer's IP address.
            //InetAddress serverAddr = InetAddress.getByName(SERVER_IP);

            Log.i("TCP Client", "C: Connecting...");

            //create a socket to make the connection with the server
            Socket socket = new Socket(ip, port);
            //set in the mainactivity that the tcp client is running -> so that its possible to communicate
            MainActivity.tcpClientRunning = true;

            try {

                mByteOutputStream = new DataOutputStream(socket.getOutputStream());

                inputS = new BufferedInputStream(socket.getInputStream());

                //in this while the client listens for the messages sent by the server
                while (mRun) {
                    while ((rxDataLen = inputS.read(rxData, rxDataLenTotal, 1)) != -1) {
                        rxDataLenTotal += rxDataLen;

                        if (rxDataLenTotal >= rxData.length) {
                            rxDataLenTotal = 0;
                            if ((rxData[0] == 0x01) && (rxData[1] == 0x02) && (rxData[4] == 0x0A)) {
                                MugContent.setMugContent(rxData);
                            }
                        }
                    }
                }
            } catch (Exception e) {

                Log.e("TCP", "S: Error", e);

            } finally {
                //the socket must be closed. It is not possible to reconnect to this socket
                // after it is closed, which means a new socket instance has to be created.
                socket.close();
                Log.i("TCP", "closing socket");
                //TODO
                // If connection is lost then we have to set the variable in main to false
                MainActivity.tcpClientRunning = false;
            }

        } catch (Exception e) {

            Log.e("TCP", "C: Error", e);
        }

    }

    //Declare the interface. The method messageReceived(String message) will must be implemented in the MyActivity
    //class at on asynckTask doInBackground
    public interface OnMessageReceived {
        public void messageReceived(String message);
    }

}