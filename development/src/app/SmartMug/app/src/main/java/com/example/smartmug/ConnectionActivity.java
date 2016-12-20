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

public class ConnectionActivity extends AppCompatActivity implements OnClickListener{

    private TCPClient mTCPClient;
    private EditText ipInput;
    private EditText port;
    private String ip ;
    private int por;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connection);

        View btnScanQRCode = findViewById(R.id.qrCodeButoon);
        btnScanQRCode.setOnClickListener(this);
        View btnManuellInput = findViewById(R.id.manuellInput);
        btnManuellInput.setOnClickListener(this);

        ipInput = (EditText)findViewById(R.id.ipAdress);
        //View ipInput = findViewById(R.id.ipAdress);
        port = (EditText)findViewById(R.id.port);
        //StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.
        //        Builder().permitNetwork().build());


    }


    public void onClick(View arg) {
        switch (arg.getId()){

            case R.id.qrCodeButoon:

                IntentIntegrator integretor = new IntentIntegrator(this);
                integretor.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE_TYPES);
                integretor.setPrompt("Scan");
                integretor.setCameraId(0);
                integretor.setBeepEnabled(false);
                integretor.setBarcodeImageEnabled(false);
                integretor.initiateScan();
                break;

            case R.id.manuellInput:

                //ip = ipInput.getText().toString();
                //por = Integer.parseInt(port.getText().toString());

                new ConnectTask().execute("");
                Intent intentConect = new Intent (this, MainActivity.class);
                startActivity(intentConect);
                break;

        }
    }

    /*
    @Override
    protected void onPause(){
        super.onPause();

        //disconnect
        mTCPClient.stopClient();
        mTCPClient =null;
    } */

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if(result != null){
            if(result.getContents() == null){
                Toast.makeText(this, "Scanning Cancelled", Toast.LENGTH_LONG).show();
            }
            else {
                Toast.makeText(this, result.getContents(),Toast.LENGTH_LONG).show();
            }
        }
        else {

            super.onActivityResult(requestCode, resultCode, data);
        }
    }

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

            //mTCPClient.run(ip,por);
            mTCPClient.run("192.168.5.10",8080);

            return null;
        }

        @Override
        protected void onProgressUpdate(String... values) {
            super.onProgressUpdate(values);
            //response received from server
            Log.d("test", "response " + values[0]);
            //process server response here....

        }
    }


}


