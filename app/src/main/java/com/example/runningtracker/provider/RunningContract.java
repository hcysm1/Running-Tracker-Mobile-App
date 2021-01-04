package com.example.runningtracker.provider;

import android.net.Uri;
//SONIA MUBASHER
//20129528

public class RunningContract {

    public static final int minTime = 1; //min time interval b/w updates
    public static final int minDistance = 1; //min distance b/w updates
    public static final String progress = "com.example.runningtracker.provider.SEND_PROGRESS";
    public static final String time = "time taken";
    public static final String distance = "distance covered";
    public static final String pace = "running pace";

    public static final String TABLE_TOTAL_RUNS = "runs";
    public static final String TABLE_DETAILS_RUN = "run_details";

    public static final String COLUMN_RUN = "_id";
    public static final String COLUMN_START = "start_time";
    public static final String COLUMN_END = "end_time";
    public static final String COLUMN_DISTANCE = "distance_covered";
    public static final String COLUMN_TIME_TAKEN = "time_taken";
    public static final String COLUMN_RUNNING_PACE = "Running_pace";

    public static final String COLUMN_DETAILS_ID = "_id";
    public static final String COLUMN_RUN_ID = "run_id";
    public static final String COLUMN_LATITUDE = "latitude";
    public static final String COLUMN_LONGITUDE = "longitude";
    public static final String COLUMN_DETAILS_TIME_TAKEN = "time_taken";


    public static final String AUTHORITY = "com.example.runningtracker.provider.MyContentProvider";
    public static final Uri RUNS_URI = Uri.parse("content://" + AUTHORITY + "/" + TABLE_TOTAL_RUNS);
    public static final Uri DETAILS_URI = Uri.parse("content://" + AUTHORITY + "/" + TABLE_DETAILS_RUN);
    public static final Uri RUNS_DETAILS_URI = Uri.parse("content://" + AUTHORITY + "/" + TABLE_TOTAL_RUNS + "/" + TABLE_DETAILS_RUN);

    public static final String[] day = {
            "EMPTY", "SUNDAY", "MONDAY", "TUESDAY", "WEDNESDAY",
            "THURSDAY", "FRIDAY", "SATURDAY"
    };


}
