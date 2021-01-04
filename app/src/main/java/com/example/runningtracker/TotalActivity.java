package com.example.runningtracker;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.runningtracker.modelClasses.totalRunsClass;

import java.util.List;
//SONIA MUBASHER
//20129528

public class TotalActivity extends AppCompatActivity {
    //declaring variables
    private List<totalRunsClass> ListOfRuns;
    DBHandler db;
    TextView total_runs; //for total runs
    TextView total_km; //for displaying total kilometers
    TextView avg_dist; //for displaying average distance
    TextView avg_pace; //for displaying average pace


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_total_activity);
        db = new DBHandler(this, null, null, 1);
        ListOfRuns = db.findRuns(); //to find all the runs from database
        total_km = findViewById(R.id.textView13); //to display total kilometers
        avg_dist = findViewById(R.id.textView14); //to display average distance
        avg_pace = findViewById(R.id.textView15); //to display average pace
        total_runs = findViewById(R.id.textView12); //to display total runs
        int totalRuns = 0;
        long totalKm = 0;
        long totalTime = 0;
        //for each run in running list
        for (int i = 0; i < ListOfRuns.size(); i = i + 2) {
            totalRuns++; //increment run
            totalRunsClass currentRun = ListOfRuns.get(i); //get index
            totalKm = totalKm + currentRun.getTotalDistance(); //get distance
            totalTime = totalTime + currentRun.getTotalTimeTaken(); //get time
        }

        //calculating total runs
        total_runs.setText(String.valueOf(totalRuns));

        //calculating total kilometers
        total_km.setText((totalKm / 1000) + "." + (totalKm % 1000 / 100) + (totalKm % 100 / 10));

        //calculating average distance
        double avgDistance = Math.round((1.0 * (totalKm / 1000.0) / totalRuns) * 100.0) / 100.0;
        avg_dist.setText(String.valueOf(avgDistance));

        //calculating average pace
        long avgPace = (long) (1.0 * totalTime / (totalKm / 1000.0));
        String avgPaceString = (avgPace / 60) + "\'" + (avgPace % 60) + "\"";
        avg_pace.setText(avgPaceString);


    }
}