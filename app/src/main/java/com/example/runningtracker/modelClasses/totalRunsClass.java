package com.example.runningtracker.modelClasses;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;

import java.sql.Timestamp;

//SONIA MUBASHER
//20129528

//model class for getting data for runs
public class totalRunsClass implements Parcelable {

    //declaring variables
    private int Run_Id;
    private Timestamp startRun;
    private Timestamp finishRun;
    private int totalDistance;
    private int totalTimeTaken;
    private int pace;

    //CONSTRUCTORS
    public totalRunsClass() {

    }

    //to get all the data for a run
    public totalRunsClass(Parcel parcel) {
        Bundle bundle = parcel.readBundle();

        this.Run_Id = bundle.getInt("run id");
        this.startRun = new Timestamp(bundle.getLong("start run"));
        this.finishRun = new Timestamp(bundle.getLong("finish run"));
        this.totalDistance = bundle.getInt("distance covered");
        this.totalTimeTaken = bundle.getInt("time taken");
        this.pace = bundle.getInt("running pace");
    }

    //GETTERS AND SETTERS

    public void setRun_Id(int run_Id) {
        this.Run_Id = run_Id;
    }


    public Timestamp getStartRun() {
        return startRun;
    }

    public void setStartRun(Timestamp startRun) {
        this.startRun = startRun;
    }

    public Timestamp getFinishRun() {
        return finishRun;
    }

    public void setFinishRun(Timestamp finishRun) {
        this.finishRun = finishRun;
    }

    public int getTotalDistance() {
        return totalDistance;
    }

    public void setTotalDistance(int totalDistance) {
        this.totalDistance = totalDistance;
    }

    public int getTotalTimeTaken() {
        return totalTimeTaken;
    }

    public void setTotalTimeTaken(int totalTimeTaken) {
        this.totalTimeTaken = totalTimeTaken;
    }

    public int getPace() {
        return pace;
    }

    public void setPace(int pace) {
        this.pace = pace;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int flags) {
        Bundle bundle = new Bundle();
        bundle.putInt("run id", Run_Id);
        bundle.putLong("start run", startRun.getTime());
        bundle.putLong("finish run", finishRun.getTime());
        bundle.putInt("distance covered", totalDistance);
        bundle.putInt("time taken", totalTimeTaken);
        bundle.putInt("running pace", pace);
        parcel.writeBundle(bundle);
    }

    public static final Creator<totalRunsClass> CREATOR = new Creator<totalRunsClass>() {
        @Override
        public totalRunsClass[] newArray(int size) {
            return new totalRunsClass[size];
        }

        @Override
        public totalRunsClass createFromParcel(Parcel parcel) {
            return new totalRunsClass(parcel);
        }
    };
}
