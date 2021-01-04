package com.example.runningtracker.modelClasses;

import java.text.DateFormatSymbols;
import java.util.ArrayList;
import java.util.List;

//SONIA MUBASHER
//20129528

//model class for getting runs for a specific month
public class RunsInMonthClass {
    private int month;
    private int year;
    private List<totalRunsClass> runs;
    private int totalRuns; //total runs per month
    private long totalDistanceCovered; //total distance covered in a month
    private long totalTimeTaken; //total time calculated in a month

    //CONSTRUCTOR
    public RunsInMonthClass(int month, int year) {
        this.month = month;
        this.year = year;
        this.runs = new ArrayList<totalRunsClass>();
        totalRuns = 0;
        totalDistanceCovered = 0;
        totalTimeTaken = 0;
    }

    //GETTERS AND SETTERS
    public int getMonth() {
        return month;
    }

    public int getYear() {
        return year;
    }

    public List<totalRunsClass> getRuns() {
        return this.runs;
    }

    public int getTotalRuns() {
        return totalRuns;
    }

    //add runs for a specific month
    public void addRuns(totalRunsClass run) {
        runs.add(run);
        totalRuns++;
        totalDistanceCovered = totalDistanceCovered + run.getTotalDistance();
        totalTimeTaken = totalTimeTaken + run.getTotalTimeTaken();
    }

    //getting string for total distance
    public String getTotalDistanceCovered() {
        return (totalDistanceCovered / 1000) + "." + (totalDistanceCovered % 1000 / 100) + (totalDistanceCovered % 100 / 10);
    }

    //getting string for average pace of all runs per month
    public String getPace() {
        long avgPace = (long) (1.0 * totalTimeTaken / (totalDistanceCovered / 1000.0));
        return (avgPace / 60) + "\'" + (avgPace % 60) + "\"";
    }

    //to get the current year and month
    public String getYearMonth() {
        return new DateFormatSymbols().getMonths()[month] + " " + year;
    }
}
