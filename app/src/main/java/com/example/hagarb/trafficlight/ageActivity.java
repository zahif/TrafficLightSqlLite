package com.example.hagarb.trafficlight;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.HashMap;

public class ageActivity extends AppCompatActivity {

    private Spinner spinner;
    private static final String[] paths1 = {"without alarms", "with alarms"};
    private static final String[] paths2 = {"ben-gurion to avital hatzadik", "avital hatazdik to ben-gurion","Aroma"};
    private static final String[] paths3 = {"show map ", "dont show map"};
//    private static final Vector<HashMap<DOUBLE,DOUBLE>> coordinates;
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_age);

        spinner = (Spinner) findViewById(R.id.spinnerExperiment);
        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(ageActivity.this,
                android.R.layout.simple_spinner_item, paths1);

        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter1);
        //spinner.setOnItemSelectedListener(this);

        spinner = (Spinner) findViewById(R.id.spinnerRoute);
        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(ageActivity.this,
                android.R.layout.simple_spinner_item, paths2);

        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter2);
        //spinner.setOnItemSelectedListener(this);


        spinner = (Spinner) findViewById(R.id.spinnerDisplay);
        ArrayAdapter<String> adapter3 = new ArrayAdapter<String>(ageActivity.this,
                android.R.layout.simple_spinner_item, paths3);

        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter3);
/////////
        Button button= (Button) findViewById(R.id.buttonSTART);
        Button buttonEnd = (Button) findViewById(R.id.buttonEND);
        Button buttonNewTester = (Button) findViewById(R.id.buttonNewTester);
        buttonNewTester.setOnClickListener (new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(ageActivity.this,MainActivity.class);
                startActivity(intent);
            }
        });

        buttonEnd.setOnClickListener (new View.OnClickListener() {
            public void onClick(View v) {

                finish();

            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //EditText age = (EditText) findViewById(R.id.editText);
                //EditText name = (EditText) findViewById(R.id.editText2);

                Intent intent = new Intent(ageActivity.this,MapsActivity.class);
                //int intAge = Integer.parseInt(age.getText().toString());
                //String sName = name.getText().toString();
                //intent.putExtra("AGE",intAge);
                //intent.putExtra("NAME",sName);
                startActivity(intent);
            }
        });


    }//onCreate


    public void onItemSelected(AdapterView<?> parent, View v, int position, long id) {

        switch (position) {
            case 0:
                // Whatever you want to happen when the first item gets selected
                break;
            case 1:
                // Whatever you want to happen when the second item gets selected
                break;
            case 2:
                // Whatever you want to happen when the thrid item gets selected
                break;
        }
    }
}//class
