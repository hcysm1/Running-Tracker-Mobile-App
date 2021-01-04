package com.example.runningtracker;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;

import com.example.runningtracker.modelClasses.RunningDetailsClass;
import com.example.runningtracker.modelClasses.totalRunsClass;
import com.example.runningtracker.provider.RunningContract;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

//SONIA MUBASHER
//20129528

public class DBHandler extends SQLiteOpenHelper {

    private ContentResolver myCR; //content resolver
    //database version
    public static final int DATABASE_VERSION = 1;
    //database name
    public static final String DATABASE_NAME = "RunningTracker.db";

    //constructor
    public DBHandler(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
        myCR = context.getContentResolver();
    }

    //creating tables
    @Override
    public void onCreate(SQLiteDatabase db) {

        //sql statement for adding total runs
        String CREATE_TABLE_RUNS = "CREATE TABLE " + RunningContract.TABLE_TOTAL_RUNS + " ("
                + RunningContract.COLUMN_RUN + " INTEGER PRIMARY KEY, " +
                RunningContract.COLUMN_START + " INTEGER, "
                + RunningContract.COLUMN_END + " INTEGER, " +
                RunningContract.COLUMN_DISTANCE + " INTEGER, "
                + RunningContract.COLUMN_TIME_TAKEN + " INTEGER, " +
                RunningContract.COLUMN_RUNNING_PACE + " INTEGER " + " )";

        //sql statement for adding the details of run
        String CREATE_TABLE_DETAILS = "CREATE TABLE " + RunningContract.TABLE_DETAILS_RUN + " ("
                + RunningContract.COLUMN_DETAILS_ID + " INTEGER PRIMARY KEY, " +
                RunningContract.COLUMN_RUN_ID + " INTEGER, "
                + RunningContract.COLUMN_LATITUDE + " DOUBLE, " +
                RunningContract.COLUMN_LONGITUDE + " DOUBLE, "
                + RunningContract.COLUMN_DETAILS_TIME_TAKEN + " INTEGER," +
                " FOREIGN KEY (" + RunningContract.COLUMN_RUN_ID + ") REFERENCES " +
                RunningContract.TABLE_TOTAL_RUNS + "(" + RunningContract.COLUMN_RUN + " )"
                + "ON DELETE CASCADE ON UPDATE CASCADE)";

        //creating tables
        db.execSQL(CREATE_TABLE_RUNS);
        db.execSQL(CREATE_TABLE_DETAILS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // on upgrade drop older tables
        db.execSQL("DROP TABLE IF EXISTS " + RunningContract.TABLE_TOTAL_RUNS);
        db.execSQL("DROP TABLE IF EXISTS " + RunningContract.TABLE_DETAILS_RUN);
        //create new tables
        onCreate(db);
    }

    //ADDING RUNS
    public int addRuns(totalRunsClass runs) {
        ContentValues values = new ContentValues();
        values.put(RunningContract.COLUMN_START, runs.getStartRun().getTime());
        values.put(RunningContract.COLUMN_END, runs.getFinishRun().getTime());
        values.put(RunningContract.COLUMN_TIME_TAKEN, runs.getTotalTimeTaken());
        values.put(RunningContract.COLUMN_DISTANCE, runs.getTotalDistance());
        values.put(RunningContract.COLUMN_RUNNING_PACE, runs.getPace());

        Uri uri = myCR.insert(RunningContract.RUNS_URI, values);

        return Integer.valueOf(uri.getLastPathSegment());
    }

    //ADDING DETAILS OF SPECIFIC RUN
    public void addDetails(List<RunningDetailsClass> runningList, int run) {
        for (int i = 0; i < runningList.size(); i++) {
            RunningDetailsClass runningDetails = runningList.get(i);
            ContentValues values = new ContentValues();
            values.put(RunningContract.COLUMN_RUN_ID, run);
            values.put(RunningContract.COLUMN_LATITUDE, runningDetails.getLatitude());
            values.put(RunningContract.COLUMN_LONGITUDE, runningDetails.getLongitude());
            values.put(RunningContract.COLUMN_DETAILS_TIME_TAKEN, runningDetails.getStartTime());

            myCR.insert(RunningContract.DETAILS_URI, values);
        }
    }

    //FINDING ALL RUNS
    public List<totalRunsClass> findRuns() {
        String[] projection = {RunningContract.COLUMN_RUN,
                RunningContract.COLUMN_START,
                RunningContract.COLUMN_END,
                RunningContract.COLUMN_DISTANCE,
                RunningContract.COLUMN_TIME_TAKEN,
                RunningContract.COLUMN_RUNNING_PACE};
        String selection = "";
        Cursor cursor = myCR.query(RunningContract.RUNS_URI,
                projection, selection, null, null);
        List<totalRunsClass> runs = new ArrayList<totalRunsClass>();
        if ((cursor != null) && (cursor.getCount() > 0)) {
            cursor.moveToFirst();
            do {
                totalRunsClass run = new totalRunsClass();
                run.setRun_Id(Integer.parseInt(cursor.getString(0)));
                run.setStartRun(new Timestamp(cursor.getLong(1)));
                run.setFinishRun(new Timestamp(cursor.getLong(2)));
                run.setTotalDistance(Integer.parseInt(cursor.getString(3)));
                run.setTotalTimeTaken(Integer.parseInt(cursor.getString(4)));
                run.setPace(Integer.parseInt(cursor.getString(5)));
                runs.add(run);
            } while (cursor.moveToNext());
            cursor.close();
        }
        return runs;
    }


}
