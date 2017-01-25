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

/*! @file  
 *  @brief This is the personal data activity of the smartmug project.
 *
 *  @defgroup privatedata Private data
 *  @brief Contains the personal data of the user of the smartmug app.
 *
 *  @addtogroup java
 *  @{
 *
 *  @addtogroup privatedata
 *  @{
 */

public class PersonalData extends AppCompatActivity {

    public static boolean sex;
    public int height;
    public static int weight;


    private EditText etHeight;
    private EditText etMaleFem;
    private EditText etWeight;
   // private RadioButton rbtnMale, rbtnFemale;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_data);
        etMaleFem = (EditText)findViewById(R.id.txtMaleFemale);
        etHeight = (EditText)findViewById(R.id.txtCM);
        etWeight = (EditText)findViewById(R.id.txtGR);
        /**
         * Get the "personalData" from the database
         */
        SharedPreferences p = getSharedPreferences("personalData", Context.MODE_PRIVATE);
        etMaleFem.setText(p.getString("sex",""));
        etHeight.setText(p.getString("height",""));
        etWeight.setText(p.getString("weight",""));
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
                /**
                 * get the "personalData" data.
                 */
                SharedPreferences preferencias = getSharedPreferences("personalData", Context.MODE_PRIVATE);
                /**
                 * define sex height and weight
                 */
                height = Integer.parseInt(etHeight.getText().toString());
                weight = Integer.parseInt(etWeight.getText().toString());
                if (etMaleFem.getText().toString().equals("male")){
                    sex = true;
                } else sex = false;
                /**
                 * Change the data
                 */
                SharedPreferences.Editor editor = preferencias.edit();
                editor.putString("sex", etMaleFem.getText().toString());
                editor.putString("height", etHeight.getText().toString());
                editor.putString("weight", etWeight.getText().toString());
                //editor.putString("sex", sex);
                /**
                 * save the new data
                 */
                editor.commit();
                finish();
                break;
        }
    }

}

/*!
 * @}
 * @}
 */
