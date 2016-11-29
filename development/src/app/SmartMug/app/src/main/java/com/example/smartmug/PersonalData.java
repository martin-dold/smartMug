package com.example.smartmug;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.content.SharedPreferences;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.ToggleButton;

public class PersonalData extends AppCompatActivity {


    private String sex;
    private String height;
    private EditText etHeight;
    private EditText etMaleFem;
   // private RadioButton rbtnMale, rbtnFemale;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_data);

        etMaleFem=(EditText)findViewById(R.id.txtMaleFemale);
        etHeight=(EditText)findViewById(R.id.txtCM);
        SharedPreferences prefe=getSharedPreferences("datos", Context.MODE_PRIVATE);
        etMaleFem.setText(prefe.getString("sex",""));
        etHeight.setText(prefe.getString("height",""));
       // etHeight.setText(prefe.getString("sex",""));
//        rbtnMale=(RadioButton)findViewById(R.id.btnMale);
//        rbtnFemale=(RadioButton)findViewById(R.id.btnFemale);

    }

    public void onClick(View arg) {
//
//        if(rbtnMale.isChecked() == true) {
//            rbtnMale.setSelected(true);
//        } else {
//            rbtnFemale.setSelected(true);
//        }
        switch (arg.getId()) {
//            case R.id.maleButton:
//                sex = "male";
//                break;
//            case R.id.femaleButton:
//                sex = "female";
//                break;
            case R.id.saveButton:
                SharedPreferences preferencias = getSharedPreferences("datos", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = preferencias.edit();
                editor.putString("sex", etMaleFem.getText().toString());
                editor.putString("height", etHeight.getText().toString());
                //editor.putString("sex", sex);
                editor.commit();
                finish();
                break;
        }
    }

}
