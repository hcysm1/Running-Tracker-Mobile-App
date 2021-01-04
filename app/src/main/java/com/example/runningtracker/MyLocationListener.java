package com.example.runningtracker;

import android.content.Context;

import com.example.runningtracker.modelClasses.totalRunsClass;
import com.example.runningtracker.modelClasses.RunningDetailsClass;
import com.example.runningtracker.provider.RunningContract;

import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import java.sql.Timestamp;
import java.util.ArrayList;
//SONIA MUBASHER
//20129528

public class MyLocationListener implements LocationListener {

    //running states of user
    public enum runningState {
        Running, //when the user is running
        paused, //when the user pauses his/her run
        stopped //when the user discards the run
    }

    //declaring variables
    private runningState state; //to set the state of user
    private Context myContext; //to set context
    private LocationManager locationManager; //location manager for location updates
    private Handler handler = new Handler(); //handler for time taken
    private totalRunsClass runs; //creating a run object
    private ArrayList<RunningDetailsClass> runningList; //adding the details to array of running list
    private int timeTaken; //time taken value
    private int distanceCovered; //value for distance
    private int RunningPace; //value for running pace
    private Location currentLocation; //storing current location of user
    DBHandler db;

    public runningState getState() {

        return this.state; //getting the current state of the user
    }


    public MyLocationListener(Context context) {
        myContext = context;
        runs = new totalRunsClass(); //creating a new run
        state = runningState.stopped; //setting current state to stopped
        timeTaken = 0; //time taken will be initialized to zero
        distanceCovered = 0; //distance covered will be initialized to zero
        RunningPace = 0; // running pace will be initialized to zero
        locationListener(); //location listener method will be called

    }

    //location listener method
    public boolean locationListener() {
        locationManager = (LocationManager) myContext.getSystemService(Context.LOCATION_SERVICE);
        try {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, RunningContract.minTime, RunningContract.minDistance, this);
        } catch (SecurityException e) {
            Log.d("g53mdp", e.toString());
            return false;
        }
        return true;
    }

    //when the user will start running this method will be called
    public void startRunning() {
        state = runningState.Running; //state will be set to running
        Timestamp currentTime = new Timestamp(System.currentTimeMillis()); //get the current time
        runs.setStartRun(currentTime); //set the time
        runningList = new ArrayList<RunningDetailsClass>();//add it to list
        updateTime(); //this method will be called to keep updating th time as the user will move
    }

    //when the user will stop running this method will be called
    public void stopRunning() {
        locationManager.removeUpdates(this);
        state = runningState.stopped; //state will be set to stopped
        //if the running list is not empty
        if (!runningList.isEmpty()) {
            Timestamp currentTime = new Timestamp(System.currentTimeMillis()); //get the current time
            runs.setFinishRun(currentTime); //set the current time
            runs.setTotalDistance(distanceCovered); //set the total distance covered
            runs.setPace(RunningPace); //set the running pace
            runs.setTotalTimeTaken(timeTaken); //set the time taken
            // save Run and RunDetails ArrayList to database
            db = new DBHandler(myContext, null, null, 1);
            int id = db.addRuns(runs);
            db.addDetails(runningList, id);
        }
    }


    //when the user will pause his run this method will be called
    public void pauseRunning() {

        state = runningState.paused; //state will be set to paused
    }

    //when the user will continue running again this metod will be called
    public void continueRunning() {
        state = runningState.Running; //state will be set to running
        updateTime(); //this method will be called to keep updating th time as the user will move
    }

    //calling runnable thread to keep updating time
    private Runnable timeHandler = new Runnable() {
        @Override
        public void run() {
            updateTime();
        }
    };

    public void updateTime() {
        //first check if the state is running
        if (state == runningState.Running) {
            updateProgress(); //then call update progress method
            handler.postDelayed(timeHandler, 1000);
            timeTaken++; //increment timer
        }
    }

    //update progress method
    private void updateProgress() {
        Intent run_progress = new Intent(); //create a new intent
        run_progress.setAction(RunningContract.progress); //set the action
        run_progress.putExtra(RunningContract.distance, distanceCovered); //adding distance to broadcast intent
        run_progress.putExtra(RunningContract.pace, RunningPace); //adding running pace to broadcast intent
        run_progress.putExtra(RunningContract.time, timeTaken); //adding time taken to broadcast intent
        myContext.sendBroadcast(run_progress); //send broadcast

    }

    //on location changed method
    @Override
    public void onLocationChanged(Location location) {

        if (state == runningState.Running && (currentLocation != null)) {
            //calculating distance between 2 coordinates
            distanceCovered = distanceCovered + (int) location.distanceTo(currentLocation); //getting the distance and incrementing
            //calculating pace
            RunningPace = (int) ((timeTaken * 1.0) / (distanceCovered * 1.0 / 1000));
            //adding run details
            RunningDetailsClass runningDetails = new RunningDetailsClass(currentLocation.getLatitude(), currentLocation.getLongitude(), timeTaken);
            runningList.add(runningDetails);
        }
        currentLocation = location; //setting the location as current location
    }


    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        // information about the signal, i.e. number of satellites
        Log.d("g53mdp", "onStatusChanged: " + provider + " " + status);
    }

    @Override
    public void onProviderEnabled(String provider) {
        // the user enabled (for example) the GPS
        Log.d("g53mdp", "onProviderEnabled: " + provider);
    }

    @Override
    public void onProviderDisabled(String provider) {
        // the user disabled (for example) the GPS
        Log.d("g53mdp", "onProviderDisabled: " + provider);
    }


}
