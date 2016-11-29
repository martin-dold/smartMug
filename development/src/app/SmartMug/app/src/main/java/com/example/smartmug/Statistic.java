package com.example.smartmug;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;

public class Statistic extends AppCompatActivity {

    private EditText numOrder;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistic);
        Bundle bundle = getIntent().getExtras();
        int count = bundle.getInt("orderCont");
        numOrder.setText(count);

    }
}
