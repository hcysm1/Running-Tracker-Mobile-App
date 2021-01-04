package com.example.runningtracker;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;

import static android.app.NotificationManager.IMPORTANCE_DEFAULT;

//SONIA MUBASHER
//20129528
public class RunningService extends Service {

    //Declaring variables
    private final IBinder myBinder = new MyLocalBinder();
    public String CHANNEL_ID = " Running Channel";
    NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID);
    private MyLocationListener listener;

    //Initializing Notification manager
    NotificationManager notificationManager;

    //-----TO DISPLAY NOTIFICATION-----//
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        notificationManager = getSystemService(NotificationManager.class);
        builder
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentTitle("Running Tracker") //sets the title
                .setContentText("Running Now") //sets the text to be displayed on notification
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        //When the user clicks on notification this will redirect the user to Running activity
        Intent notificationIntent = new Intent(this, RunActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(pendingIntent);

        //Creating a notification channel
        NotificationChannel audioChannel = new NotificationChannel(CHANNEL_ID, "RunningTracker", IMPORTANCE_DEFAULT);
        audioChannel.setDescription("Running notification");
        notificationManager.createNotificationChannel(audioChannel);

        //To display the notification
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
        int notificationId = 2;
        notificationManager.notify(notificationId, builder.build());
        return super.onStartCommand(intent, flags, startId);
    }
    //-----TO DISPLAY NOTIFICATION-----//

    //To remove the notification
    public void removeNotification() {
        NotificationManager Manager = (NotificationManager) getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);
        Manager.cancel(0);
    }

    //default constructor for running service
    public RunningService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        listener = new MyLocationListener(RunningService.this);
        return myBinder;
    }

    @Override
    public void onTaskRemoved(Intent rootIntent) {

        super.onTaskRemoved(rootIntent);
    }

    public class MyLocalBinder extends Binder {

        RunningService service() {
            return RunningService.this;
        }
    }

    //getting the current state  from location listener
    public MyLocationListener.runningState getState() {
        return listener.getState();
    }

    //calling start running method from location listener
    public void startRunning() {
        listener.startRunning();
    }

    //calling stop running method from location listener
    public void stopRunning() {
        listener.stopRunning();
        stopSelf();
    }

    //calling pause running method from location listener
    public void pauseRunning() {
        listener.pauseRunning();
    }

    //calling continue running method from location listener
    public void continueRunning() {
        listener.continueRunning();
    }

    //To destroy the Running service
    @Override
    public void onDestroy() {
        super.onDestroy();
        removeNotification();
        listener.stopRunning();
    }
}
