package com.example.smartmug;

import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.net.InetAddress;
import java.net.UnknownHostException;


/*! @file  
 *  @brief This is the connection activity of the smartmug project.
 *
 *  @defgroup connection Connection
 *  @brief Adds TCP functionality to the smartmug app.
 *
 *  @addtogroup java
 *  @{
 *
 *  @addtogroup connection
 *  @{
 */

public class ConnectionActivity extends AppCompatActivity implements OnClickListener{

    /**
     * TCP Client
     */
    private TCPClient mTCPClient;
    /**
     * Textfield for the IP
     */
    private EditText ipInput;
    /**
     * Textfield for the port
     */
    private EditText port;

    /**
     * value of the connected smartmug ip
     */
    private String ip ;
    /**
     * value of the connected smartmug port
     */
    private int portInt;

    /**
     * value if qrCode is used
     */
    private boolean qrCodeActive =false;

    /**
     * Local variable used to store IP address resolved by hostname.
     */
    private InetAddress serverAddr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_connection);
        /**
         * QR Code Button
         */
        View btnScanQRCode = findViewById(R.id.qrCodeButoon);
        /**
         * Listener for QR Code Button
         */
        btnScanQRCode.setOnClickListener(this);
        /**
         * Manuell Input Button
         */
        View btnManuellInput = findViewById(R.id.manuellInput);
        /**
         * Listener for Manuell Input Button
         */
        btnManuellInput.setOnClickListener(this);

        /**
         * Text field for IP Adress
         */
        ipInput = (EditText)findViewById(R.id.ipAdress);
        /**
         * Text field for port
         */
        port = (EditText)findViewById(R.id.port);
    }

    /**
     * Click on Listener Method
     * @param arg
     */
    public void onClick(View arg) {
        switch (arg.getId()){

            case R.id.qrCodeButoon:
                /**
                 * Initialize the Barcode Scanner
                 */
                IntentIntegrator integretor = new IntentIntegrator(this);
                integretor.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE_TYPES);
                integretor.setPrompt("Scan");
                integretor.setCameraId(0);
                integretor.setBeepEnabled(false);
                integretor.setBarcodeImageEnabled(false);
                integretor.initiateScan();
                break;

            case R.id.manuellInput:

                /**
                 * IP Input field
                 */
                ip = ipInput.getText().toString();
                portInt = Integer.parseInt(port.getText().toString());
                buildNewTCPConnection();
                break;

        }
    }

    /**
     * QR Code Method
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if(result != null){
            if(result.getContents() == null){
                Toast.makeText(this, "Scanning Cancelled", Toast.LENGTH_LONG).show();
            }
            else {
                Toast.makeText(this, "Searching for smartmug id: " + result.getContents(),Toast.LENGTH_LONG).show();
                qrCodeActive = true;
                ip = MainActivity.mNsdHelper.getIpString(result.getContents());
                if(ip != null)
                {
                    /* If we found a valid IP we very likely have a valid port too. */
                    portInt = MainActivity.mNsdHelper.getPortNum(result.getContents());
                    buildNewTCPConnection();
                }
                else
                {
                    Log.e("TCP", "SmartMug service not discovered yet.");
                }
            }
        }
        else {

            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    /**
     * build the TCP Connection
     */
    private void buildNewTCPConnection() {
        new ConnectTask().execute("");
        /**
         * if the TCP Connection dont failed go back to the MainActivity
         * else No Connection
         */
        if(MainActivity.tcpClientRunning == true){
            Intent intentConect = new Intent (this, MainActivity.class);
            startActivity(intentConect);
        } else {
            Toast.makeText(this, "No Connection! Maybe wrong IP Adress ", Toast.LENGTH_LONG).show();
        }

    }

    /**
     * Connect class as asynch task
     */
    public class ConnectTask extends AsyncTask<String, String, TCPClient> {

        @Override
        protected TCPClient doInBackground(String... message) {

            //we create a TCPClient object
            mTCPClient = new TCPClient(new TCPClient.OnMessageReceived() {
                @Override
                //here the messageReceived method is implemented
                public void messageReceived(String message) {
                    //this method calls the onProgressUpdate
                    publishProgress(message);
                }
            });

            /**
             * two cases: one for the QR Code connection, second for the manuell Input
             */
            //if (qrCodeActive == true){
/*                try {
                    serverAddr = InetAddress.getByName(ip);
                    mTCPClient.run(serverAddr.getHostAddress(),portInt);
                    qrCodeActive = false;
                } catch (UnknownHostException e) {
                    e.printStackTrace();
                }*/
            //}

            if(ip != null){
                mTCPClient.run(ip,portInt);
            } else {
                Toast.makeText(getApplicationContext(), "NO IP", Toast.LENGTH_LONG).show();
            }
            return null;
        }

        /**
         * Update Progeress
         * @param values
         */
        @Override
        protected void onProgressUpdate(String... values) {
            super.onProgressUpdate(values);
            //response received from server
            //Log.d("test", "response " + values[0]);
            //process server response here....

        }
    }


}

/*!
 * @}
 * @}
 */
