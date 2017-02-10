package com.example.smartmug;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.CountDownTimer;
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
import java.util.regex.Matcher;
import java.util.regex.Pattern;


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
     * Local variable used to store IP address resolved by hostname.
     */
    private InetAddress serverAddr;

    private static final Pattern IP_ADDRESS
            = Pattern.compile(
            "((25[0-5]|2[0-4][0-9]|[0-1][0-9]{2}|[1-9][0-9]|[1-9])\\.(25[0-5]|2[0-4]"
                    + "[0-9]|[0-1][0-9]{2}|[1-9][0-9]|[1-9]|0)\\.(25[0-5]|2[0-4][0-9]|[0-1]"
                    + "[0-9]{2}|[1-9][0-9]|[1-9]|0)\\.(25[0-5]|2[0-4][0-9]|[0-1][0-9]{2}"
                    + "|[1-9][0-9]|[0-9]))");

    public SharedPreferences p;

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

        p = getSharedPreferences("ipAdress", Context.MODE_PRIVATE);
        ipInput.setText(p.getString("ip",""));
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

                SharedPreferences.Editor editor = p.edit();
                editor.putString("ip", ip);
                /**
                 * save the new data
                 */
                editor.commit();
                finish();
                if( (ip != null) && (portInt != 0) )
                {
                    Matcher matcher = IP_ADDRESS.matcher(ip);
                    if (matcher.matches()) {
                        // Entered string is a valid ip address
                        buildNewTCPConnection();
                    }
                    else
                    {
                        Toast.makeText(this, "Enter valid IP address.", Toast.LENGTH_LONG).show();
                    }
                }
                else
                {
                    Toast.makeText(this, "Invalid connection settings entered.", Toast.LENGTH_LONG).show();
                }
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
                /* Get the smartmugId that is contained in the qr-code. */
                String smartmugId = result.getContents();
                /* Ask our NSD discovery if that name/id was found in the network by
                *  reading IP address and port number. */
                ip = MainActivity.mNsdHelper.getIpString(smartmugId);
                portInt = MainActivity.mNsdHelper.getPortNum(smartmugId);
                if((ip != null) && (portInt != 0))
                {
                    Toast.makeText(this, "Connecting to smartmug id: \"" + smartmugId + "\"" ,Toast.LENGTH_LONG).show();
                    Log.i("TCP", "Connecting to smartmug id: " + smartmugId);
                    buildNewTCPConnection();
                }
                else
                {
                    Toast.makeText(this, "SmartMug \"" + smartmugId + "\" not found yet. Try again!",Toast.LENGTH_LONG).show();
                    Log.e("TCP", "SmartMug service not discovered yet: " + smartmugId);
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

        /* Execute asynchronous task that tries to connect to the smartmug. */
        new ConnectTask().execute("");

        /* Lets give 1s to connect to the smartmug. */
        new CountDownTimer(1000, 1) {
            public void onFinish() {
                // When timer is finished
                if(MainActivity.tcpClientRunning == true){
                    Toast.makeText(ConnectionActivity.this, "Connected to smartmug.",Toast.LENGTH_LONG).show();
                    Intent intentConect = new Intent (ConnectionActivity.this, MainActivity.class);
                    startActivity(intentConect);
                }
                else
                {
                    Toast.makeText(ConnectionActivity.this, "Unable to connect to smartmug.",Toast.LENGTH_LONG).show();
                }
            }

            public void onTick(long millisUntilFinished) {
                // millisUntilFinished    The amount of time until finished.

                // Smartmug: simply wait until timer is finished and executes onFinished().
            }
        }.start();
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

            /* We actually checked IP and port before, but lets be safe. */
            if( (ip != null) && (portInt != 0) ){
                mTCPClient.run(ip,portInt);
            } else {
                Toast.makeText(getApplicationContext(), "No valid IP connection to smartmug.", Toast.LENGTH_LONG).show();
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
        }
    }


}

/*!
 * @}
 * @}
 */
