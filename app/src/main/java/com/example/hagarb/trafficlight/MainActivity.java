package com.example.hagarb.trafficlight;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    DatabaseHelper myDb;
    EditText editID;
    Button button;

    public static void print(Context c, String s){
        Toast.makeText(c, s, Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myDb = new DatabaseHelper(this);

        button= (Button) findViewById(R.id.buttonSubmit);

        editID = (EditText) findViewById(R.id.IDeditText);

        /*String subjectString = editID.getText().toString();

        if (subjectString.length() != 9){
            Toast.makeText(MainActivity.this, "Invalid ID, please fix", Toast.LENGTH_LONG).show();
        }
        else{*/
            AddData();
        //}
        // int intID = Integer.parseInt(editID.getText().toString());
         // intent.putExtra("ID",intID);



    };



    public void AddData(){
        button.setOnClickListener(
                new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            try {

                                String subjectString = editID.getText().toString();

                                if (subjectString.length() != 9){
                                    Toast.makeText(MainActivity.this, "Invalid ID, please fix", Toast.LENGTH_LONG).show();
                                }
                                else{
                                    boolean isInserted = myDb.insertData(Integer.valueOf(editID.getText().toString()));
                                    if (isInserted)
                                        Toast.makeText(MainActivity.this, "Data Inserted", Toast.LENGTH_LONG).show();
                                    else
                                        Toast.makeText(MainActivity.this, "Data Not Inserted", Toast.LENGTH_LONG).show();
                                    Intent intent = new Intent(MainActivity.this, PersonalDeatailsActivity.class); //TODO change to ageActivity
                                    startActivity(intent);
                                }

                            }
                            catch(Exception noDataInserted ){}

                        }


                }
        );

    }


}
