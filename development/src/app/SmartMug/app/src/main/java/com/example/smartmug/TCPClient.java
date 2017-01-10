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
/**
 * Created by FuechsleXD on 04.12.2016.
 */

public class TCPClient {


    // message to send to the server
    private String mServerMessage;
    // sends message received notifications
    private OnMessageReceived mMessageListener = null;
    // while this is true, the server will continue running
    private boolean mRun = false;
    // used to send messages
    private static BufferedWriter mBufferOut;

    private static DataOutputStream mByteOutputStream;
    // used to read messages from the server
    private BufferedReader mBufferIn;

    private byte[] byteArray;
    private byte Byte;

    /**
     * Constructor of the class. OnMessagedReceived listens for the messages received from server
     */
    public TCPClient(OnMessageReceived listener) {
        mMessageListener = listener;
    }

    /**
     * Sends the message entered by client to the server
     *
     * @param message text entered by client
     */
    /*
    public static void sendMessage(String message) {
        if (mBufferOut != null && !mBufferOut.checkError()) {
            mBufferOut.println(message);
            mBufferOut.flush();
        }
    } */

    public static void sendMessageByteArray(byte[] array){

        if (mByteOutputStream != null) {
            //mBufferOut.


            try {
                mByteOutputStream.write(array);
                mByteOutputStream.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
            // mBufferOut.write("\n");

            //DataInputStream dOut = new DataOutputStream(so)


           // mBufferOut.println(array);

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

        mMessageListener = null;
        mBufferIn = null;
        mByteOutputStream = null;
        mServerMessage = null;

    }
/*    public void stopClient() {

        mRun = false;

        if (mBufferOut != null) {
            mBufferOut.flush();
            mBufferOut.close();
        }

        mMessageListener = null;
        mBufferIn = null;
        mBufferOut = null;
        mServerMessage = null;
    }
*/

    public void run(String ip, int port) {

        mRun = true;

        try {
            //here you must put your computer's IP address.
            //InetAddress serverAddr = InetAddress.getByName(SERVER_IP);

            Log.e("TCP Client", "C: Connecting...");

            //create a socket to make the connection with the server
            Socket socket = new Socket(ip, port);
            //set in the mainactivity that the tcp client is running -> so that its possible to communicate
            MainActivity.tcpClientRunning = true;

            try {

                //sends the message to the server
                // mBufferOut = newPrintWriter(ne)(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()), true);

                mByteOutputStream = new DataOutputStream(socket.getOutputStream());

                //receives the message which the server sends back
                mBufferIn = new BufferedReader(new InputStreamReader(socket.getInputStream()));


                //in this while the client listens for the messages sent by the server
                while (mRun) {


                    mServerMessage = mBufferIn.readLine();

                    if (mServerMessage != null && mMessageListener != null) {
                        //call the method messageReceived from MyActivity class

                        byteArray = mServerMessage.getBytes();
                        //output the byte value
                        MugContent.setMugContent(byteArray);
                        mMessageListener.messageReceived(mServerMessage);
                    }

                }

                Log.e("RESPONSE FROM SERVER", "S: Received Message: '" + mServerMessage + "'");

            } catch (Exception e) {

                Log.e("TCP", "S: Error", e);

            } finally {
                //the socket must be closed. It is not possible to reconnect to this socket
                // after it is closed, which means a new socket instance has to be created.
                socket.close();
                Log.e("TCP closing socket", "");
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