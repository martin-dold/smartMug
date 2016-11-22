package com.example.smartmug;


import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;



public class MainActivity extends AppCompatActivity implements OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        View btnManuellInput = findViewById(R.id.connectMugButton);
        btnManuellInput.setOnClickListener(this);

    }

    public void onClick(View arg){
        switch (arg.getId()){

            case R.id.connectMugButton:
                Intent intent = new Intent (this, ConnectionActivity.class);
                startActivity(intent);
                break;


        }
    }


}
