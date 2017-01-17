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
    /** Total number of bytes read within one frame. */
    private int rxDataLenTotal = 0;
    /** Holds the length field of the SmartMug Protocol frame
     *  and thereby tells the len to read, i.e. the length of the frame. */
    private int lenToRead = 0;
    /** Single byte array memory to store the current byte to be processed within a frame. */
    private byte[] currentByte = new byte[1];
    /** Current tag value read from current frame. */
    private int currentTag;

    private static boolean isDataToBeSent = false;

    private static byte[] txData = new byte[256];

    /** States of the state machine to parse the SmartMug Protocol that consists of:
     *  [TAG] [LEN] [VALUE] [EOF = '\n'] */
    private enum PARSER_STATE
    {
        PARSER_STATE_READ_TAG,
        PARSER_STATE_READ_LENGTH,
        PARSER_STATE_READ_VALUE,
        PARSER_STATE_READ_EOF
    }

    private PARSER_STATE parser_state;


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
            txData = array;
            isDataToBeSent = true;
            /*
            try {
                mByteOutputStream.write(array);
                mByteOutputStream.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
            */
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
        rxDataLenTotal = 0;
        lenToRead = 0;
        parser_state = PARSER_STATE.PARSER_STATE_READ_TAG;
        currentTag = 0;
    }

    public void run(String ip, int port) {

        mRun = true;

        try {
            //here you must put your computer's IP address.


            Log.i("TCP Client", "C: Connecting...");

            //create a socket to make the connection with the server
            Socket socket = new Socket(ip, port);
            //set in the mainactivity that the tcp client is running -> so that its possible to communicate
            MainActivity.tcpClientRunning = true;
            // Reset state machine before start
            parser_state = PARSER_STATE.PARSER_STATE_READ_TAG;

            try {

                mByteOutputStream = new DataOutputStream(socket.getOutputStream());

                inputS = new BufferedInputStream(socket.getInputStream());

                //in this while the client listens for the messages sent by the server
                while (mRun) {
                    /* Read byte per byte and pass it through the parsing state machine,
                     * i.e. through the switch case.
                     */
                    while (inputS.read(currentByte, 0, 1) != -1) {

                        switch (parser_state)
                        {
                            case PARSER_STATE_READ_TAG:
                                currentTag = (int) currentByte[0];
                                rxDataLenTotal = 0;
                                lenToRead = 0;
                                parser_state = PARSER_STATE.PARSER_STATE_READ_LENGTH;
                                break;

                            case PARSER_STATE_READ_LENGTH:
                                lenToRead = (int) currentByte[0];
                                parser_state = PARSER_STATE.PARSER_STATE_READ_VALUE;
                                break;

                            case PARSER_STATE_READ_VALUE:
                                /* Store current byte to our rx data buffer. */
                                rxData[rxDataLenTotal] = currentByte[0];
                                /* Increment length counter. */
                                rxDataLenTotal++;
                                /* Check if we read the complete frame already. */
                                if(rxDataLenTotal == lenToRead)
                                {
                                    /* The complete data was read. Go to final state. */
                                    parser_state = PARSER_STATE.PARSER_STATE_READ_EOF;
                                }
                                break;

                            case PARSER_STATE_READ_EOF:
                                if(currentByte[0] == 0x0A)
                                {
                                    /* The complete frame is read successfully. Pass it to the mug content. */
                                    MugContent.setMugContent(currentTag, lenToRead, rxData);
                                }
                                /* Reset state machine for next frame either way. */
                                parser_state = PARSER_STATE.PARSER_STATE_READ_TAG;
                                break;
                        }

                        if(isDataToBeSent)
                        {
                            try {
                                mByteOutputStream.write(txData);
                                mByteOutputStream.flush();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            isDataToBeSent = false;
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