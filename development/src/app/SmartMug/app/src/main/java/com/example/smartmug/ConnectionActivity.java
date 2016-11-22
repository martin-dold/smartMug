package com.example.smartmug;

import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

public class ConnectionActivity extends AppCompatActivity implements OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connection);

        View btnScanQRCode = findViewById(R.id.qrCodeButoon);
        btnScanQRCode.setOnClickListener(this);
        View btnManuellInput = findViewById(R.id.manuellInput);
        btnManuellInput.setOnClickListener(this);
    }


    public void onClick(View arg){
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
                Intent intent = new Intent (this, ManuellInput.class);
                startActivity(intent);
                break;

        }
    }

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
}
