package com.example.smartmug;

import android.content.Context;
import android.location.OnNmeaMessageListener;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.*;
import java.net.Socket;
import java.lang.System;
import java.net.SocketTimeoutException;
import java.nio.ByteBuffer;

/*! @file  
 *  @brief This is the TCP client class of the smartmug project.
 *
 *  @defgroup java_tcp TCP client
 *  @brief Adds TCP client connectivity to the smartmug app.
 *
 *  @addtogroup java
 *  @{
 *
 *  @addtogroup java_tcp
 *  @{
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
    /** @brief True if data shall be sent to the smartmug (asynchronously). False otherwise. */
    private static boolean isDataToBeSent = false;
    /** @brief Array to store the data to be sent to the smartmug. */
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

    /** @brief Current state of the smartmug protocol parser. */
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
            /* Store the TX data request and handle it asynchronous run(). */
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
        /*set the tcpClient is not running anymore in the MainActivity*/
        MainActivity.tcpClientRunning = false;
    }

    public void run(String ip, int port) {

        mRun = true;

        try {
            Log.i("TCP Client", "C: Connecting...");

            //create a socket to make the connection with the server
            Socket socket = new Socket(ip, port);
            // Uncomment the next line if connection timeout occurs
            //socket.setSoTimeout(20000);
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
            } catch (SocketTimeoutException e) {
                Log.e("TCP", "Connection lost.", e);

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

/*!
 * @}
 * @}
 */