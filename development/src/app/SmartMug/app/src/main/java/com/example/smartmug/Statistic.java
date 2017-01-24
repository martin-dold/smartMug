package com.example.smartmug;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;

/*! @file  
 *  @brief This is the statistics activity of the smartmug project.
 *
 *  @defgroup statistics Statistics
 *  @brief Contains the statistics of the user of the smartmug app.
 *
 *  @addtogroup java
 *  @{
 *
 *  @addtogroup statistics
 *  @{
 */

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

/*!
 * @}
 * @}
 */
