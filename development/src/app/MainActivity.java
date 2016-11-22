package com.example.larraitz.smartmug;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private EditText et1;
    private EditText et2;
    private TextView tv1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //tv1=(TextView)findViewById(R.id.tv1);
        et1=(EditText)findViewById(R.id.et1);
        et2=(EditText)findViewById(R.id.et2);
        tv1=(TextView)findViewById(R.id.tv1);
    }

    public void ConectToMug(View v)
    {
//       // Button button = (Button) v;
//        String valor1=et1.getText().toString();
//        String valor2=et2.getText().toString();
//        int nro1=Integer.parseInt(valor1);
//        int nro2=Integer.parseInt(valor2);
//        int suma=nro1+nro2;
//        String resu=String.valueOf(suma);
//        tv1.setText(resu);
        Intent i = new Intent(this, Main2.class );
        startActivity(i);
    }
}
