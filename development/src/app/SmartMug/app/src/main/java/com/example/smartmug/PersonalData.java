package com.example.smartmug;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.content.SharedPreferences;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;
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
    public static int height;
    public static int weight;


    private EditText etHeight;
    private EditText etMaleFem;
    private EditText etWeight;
   // private RadioButton rbtnMale, rbtnFemale;

    int radiobuttonid = -1;
    private static RadioGroup radioGroup;
    private static RadioButton male,female,gender;


    public SharedPreferences p;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_data);
       // etMaleFem = (EditText)findViewById(R.id.txtMaleFemale);
        etHeight = (EditText)findViewById(R.id.txtCM);
        etWeight = (EditText)findViewById(R.id.txtGR);
        /**
         * Get the "personalData" from the database
         */
        p = getSharedPreferences("personalData", Context.MODE_PRIVATE);

        etHeight.setText(p.getString("height",""));
        etWeight.setText(p.getString("weight",""));
        sex = p.getBoolean("sex",false);

        radioGroup = (RadioGroup)findViewById(R.id.radiogroup1);



        male=(RadioButton)findViewById(R.id.radioButtonMale);
        female=(RadioButton)findViewById(R.id.radioButtonFemale);

        if(sex ==true){
            male.setChecked(true);
        } else female.setChecked(true);
    }


    public void onClick(View arg) {

        radiobuttonid = radioGroup.getCheckedRadioButtonId();
        if (radiobuttonid != -1){
            gender =(RadioButton)findViewById(radiobuttonid);
        }
        radiobuttonid = -1;


        switch (arg.getId()) {

            case R.id.saveButton:

                if(gender !=null) {
                    if (gender.getId() == male.getId()) {
                        //Toast.makeText(this, "Malesss", Toast.LENGTH_LONG).show();
                        sex = true;


                    } else if (gender.getId() == female.getId()) {
                        //Toast.makeText(this, "Female", Toast.LENGTH_LONG).show();
                        sex = false;

                    } else break;
                }

                if(etHeight.getText().toString().trim().length() <= 0){
                    Toast.makeText(this, "Height is not defined", Toast.LENGTH_LONG).show();
                    break;
                }

                if (etWeight.getText().toString().trim().length() <= 0){
                    Toast.makeText(this, "Weight is not defined", Toast.LENGTH_LONG).show();
                    break;
                }


                /**
                 * get the "personalData" data.
                 */
                //SharedPreferences preferencias = getSharedPreferences("personalData", Context.MODE_PRIVATE);

                /**
                 * define sex height and weight
                 */
                height = Integer.parseInt(etHeight.getText().toString());
                weight = Integer.parseInt(etWeight.getText().toString());

                /**
                 * Change the data
                 */
                SharedPreferences.Editor editor = p.edit();
                //editor.putString("sex", etMaleFem.getText().toString());
                editor.putString("height", etHeight.getText().toString());
                editor.putString("weight", etWeight.getText().toString());
                editor.putBoolean("sex",sex);
                //editor.putString("sex", sex);
                /**
                 * save the new data
                 */
                editor.commit();
                finish();
                break;
        }
    }

    private void storeData() {
    }

}

/*!
 * @}
 * @}
 */
