package com.example.runningtracker.modelClasses;

//SONIA MUBASHER
//20129528

//model class for getting running details
public class RunningDetailsClass {

    private int id; //id

    private double latitude; //latitude
    private double longitude; //longitude
    private int startTime; //start time when the user starts running

    //CONSTRUCTORS
    public RunningDetailsClass() {

    }

    public RunningDetailsClass(double latitude, double longitude, int startTime) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.startTime = startTime;
    }

    //GETTERS AND SETTERS
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public double getLatitude() {
        return latitude;
    }


    public double getLongitude() {
        return longitude;
    }


    public int getStartTime() {
        return startTime;
    }


}
