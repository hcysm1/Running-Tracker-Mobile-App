package com.example.runningtracker;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

//SONIA MUBASHER
//20129528
public class MainActivity extends AppCompatActivity {
    //declaring variables
    Button start;
    Button Run_History;
    Button total;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        start = findViewById(R.id.startRunning); //start button to track running
        Run_History = findViewById(R.id.History);
        total = findViewById(R.id.button);
        checkPermissionForLocation(); //to allow the app to access device's location

        //on click listener for start button
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent Run = new Intent(MainActivity.this, RunActivity.class);
                startActivity(Run); //start Run activity

            }
        });

        //on click listener for History button
        Run_History.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent history = new Intent(MainActivity.this, RunHistoryActivity.class);
                startActivity(history); //start History activity

            }
        });

        //on click listener for total button
        total.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent total = new Intent(MainActivity.this, TotalActivity.class);
                startActivity(total); //start total activity

            }
        });
    }

    //checking runtime permissions
    //when the user will run the app user will be requested to allow permission
    private void checkPermissionForLocation() {
        boolean fineLocation = ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED;
        if (!fineLocation) {
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        }

    }
}