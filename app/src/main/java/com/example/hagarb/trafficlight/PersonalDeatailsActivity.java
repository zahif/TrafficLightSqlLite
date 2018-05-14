package com.example.hagarb.trafficlight;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class PersonalDeatailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_deatails);
        Button buttonOK= (Button) findViewById(R.id.buttonOK);
        buttonOK.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                EditText name = (EditText) findViewById(R.id.editTextName);
                EditText age = (EditText) findViewById(R.id.editTextAge);

                try {

                    Intent intent = new Intent(PersonalDeatailsActivity.this, ageActivity.class); //TODO change to ageActivity
                    int subjectAge = Integer.parseInt(age.getText().toString());

                    String subjectName = name.getText().toString();
                    if (subjectName.length()>2 && subjectAge >= 18 && subjectAge <=120)
                    {
                        startActivity(intent);
                    }
                    else if ((subjectAge<18 || subjectAge >120) && subjectName.length()<2){
                        Toast.makeText(PersonalDeatailsActivity.this, "Ivalid data, please fix name and age", Toast.LENGTH_LONG).show();
                    }

                    else if(subjectName.length() <2){
                        Toast.makeText(PersonalDeatailsActivity.this, "Invalid name, please fix", Toast.LENGTH_LONG).show();
                    }
                    else {
                        Toast.makeText(PersonalDeatailsActivity.this, "Invalid age, please fix", Toast.LENGTH_LONG).show();
                    }
                }
                catch(Exception noDataInserted ){}
            }
        });

    }

}
