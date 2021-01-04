package com.example.runningtracker;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.runningtracker.modelClasses.RunsInMonthClass;
import com.example.runningtracker.modelClasses.totalRunsClass;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
//SONIA MUBASHER
//20129528

public class RunHistoryActivity extends AppCompatActivity {

    private List<totalRunsClass> runningList; //array of runs
    private ArrayList<RunsInMonthClass> months = new ArrayList<RunsInMonthClass>(); //array of months
    RecyclerView monthList; //recyclerview for months
    RecyclerView.LayoutManager layoutManager;
    MonthRecycler adapter; //adapter for month recyclerview
    DBHandler db; //database

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_run_history);
        monthList = findViewById(R.id.recyclerView);
        layoutManager = new LinearLayoutManager(this);
        monthList.setLayoutManager(layoutManager);
        adapter = new MonthRecycler(months);
        monthList.setAdapter(adapter); //setting adapter
        db = new DBHandler(this, null, null, 1);
        runningList = db.findRuns();

        //to set data for recycler view
        if (!runningList.isEmpty()) {

            totalRunsClass run = runningList.get(0);
            Calendar current = Calendar.getInstance();
            current.setTimeInMillis(run.getStartRun().getTime());

            RunsInMonthClass month = new RunsInMonthClass(current.get(Calendar.MONTH), current.get(Calendar.YEAR));
            month.addRuns(run);

            for (int i = 2; i < runningList.size(); i = i + 2) {
                run = runningList.get(i);
                current.setTimeInMillis(run.getStartRun().getTime());

                int currMonth = current.get(Calendar.MONTH); //get current month
                int currYear = current.get(Calendar.YEAR); //get current year
                if (currMonth > month.getMonth() || currYear > month.getYear()) {
                    months.add(month);
                    month = new RunsInMonthClass(currMonth, currYear);
                }
                month.addRuns(run); //add runs for that month
            }
            months.add(month);
            adapter.notifyDataSetChanged();
        }

    }
}