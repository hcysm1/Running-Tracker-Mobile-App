package com.example.runningtracker;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.runningtracker.provider.RunningContract;

//SONIA MUBASHER
//20129528

public class RunActivity extends AppCompatActivity {
    //declaring variables
    private Button StartRun; //button to start running
    private Button PauseRun; //button to pause the run
    private Button StopRun; //button to discard the run
    private TextView DistanceCovered; //text view to display total dstance covered
    private TextView TimeTaken; //text view to display total time taken
    private TextView PaceView; //text view to display user's running pace
    private BroadcastReceiver broadcastReceiver; //broadcast receiver
    private RunningService runningService; //running service
    private Intent service; //service intent
    boolean isBound = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_run);

        StartRun = findViewById(R.id.start);
        PauseRun = findViewById(R.id.pause);
        StopRun = findViewById(R.id.stop);
        DistanceCovered = findViewById(R.id.distance);
        TimeTaken = findViewById(R.id.time);
        PaceView = findViewById(R.id.pace);

        //on click listener for Start button
        StartRun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                runningService.continueRunning(); //call continue running mehod from running service
                StartRun.setText("Running"); //change the text to running when the user starts running
                StopRun.setText("Stop");
                PauseRun.setText("Pause");
            }
        });

        //on click listener for stop button
        StopRun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                runningService.stopRunning(); //call stop running method from running service
                StartRun.setText("Start");
                StopRun.setText("Stopped"); //change the text to stopped when the user stops running
                PauseRun.setText("Pause");
                finish();
            }
        });

        //on click listener for pause button
        PauseRun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                runningService.pauseRunning(); //call pause running method from running service
                StartRun.setText("Start");
                StopRun.setText("Stop");
                PauseRun.setText("Paused"); //change the text to paused when the user pauses running
            }
        });
    }


    //Service connection to connect to Running Service
    private ServiceConnection myConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            RunningService.MyLocalBinder binder = (RunningService.MyLocalBinder) service;
            runningService = binder.service();
            isBound = true;
            //if the user is not running create a new run by calling start running method from running service
            if (runningService.getState() == MyLocationListener.runningState.stopped) {
                runningService.startRunning();
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            isBound = false;
        }
    };

    @Override
    protected void onResume() {
        super.onResume();
        //if service is null create a new service
        if (service == null) {
            service = new Intent(this, RunningService.class);
            startService(service);
        }
        //and create a new broadcast receiver
        if (broadcastReceiver == null) {
            setReceiver();
        }
        bindService(service, myConnection, Context.BIND_AUTO_CREATE);
    }

    //to set the broadcast Receiver
    private void setReceiver() {
        broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                Bundle bundle = intent.getExtras();
                update(bundle);
            }
        };
        IntentFilter filter = new IntentFilter();
        filter.addAction(RunningContract.progress);
        registerReceiver(broadcastReceiver, filter);
    }

    //pass all the values and update the user interface of tracker application
    private void update(Bundle bundle) {
        int time_taken = bundle.getInt(RunningContract.time); //get the time
        int distance = bundle.getInt(RunningContract.distance); //get the distance
        int pace = bundle.getInt(RunningContract.pace); //get the pace

        //dividing the distance by 1000 to display the distance in km
        DistanceCovered.setText((distance / 1000) + "." + (distance % 1000 / 100) + (distance % 100 / 10) + "km"); //set the text of distance text view
        TimeTaken.setText(time_taken / 60 + ":" + (time_taken % 60 < 10 ? "0" + (time_taken % 60) : (time_taken % 60))); //set the text of time text view
        PaceView.setText((pace / 60) + "'" + (pace % 60) + "\""); //set the text of pace text view


    }


    @Override
    protected void onPause() {
        super.onPause();
        isBound = false;
        if (myConnection != null)
            unbindService(myConnection);
        if (broadcastReceiver != null) {
            unregisterReceiver(broadcastReceiver);
            broadcastReceiver = null;
        }
    }


}