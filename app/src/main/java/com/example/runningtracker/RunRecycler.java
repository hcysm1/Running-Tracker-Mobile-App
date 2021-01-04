package com.example.runningtracker;

import android.annotation.SuppressLint;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.runningtracker.modelClasses.totalRunsClass;
import com.example.runningtracker.provider.RunningContract;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

//SONIA MUBASHER
//20129528

public class RunRecycler extends RecyclerView.Adapter<RunRecycler.RunViewHolder> {

    List<totalRunsClass> runList;

    public static class RunViewHolder extends RecyclerView.ViewHolder {


        TextView date; //for date
        TextView day; //for current day
        TextView distance; //for current distance
        TextView pace; //for pace
        TextView time; //for getting time

        public RunViewHolder(@NonNull View itemView) {
            super(itemView);

            //getting reference of textViews and binding to view holder
            date = itemView.findViewById(R.id.date_id);
            day = itemView.findViewById(R.id.day_id);
            distance = itemView.findViewById(R.id.distance_id);
            pace = itemView.findViewById(R.id.pace_id);
            time = itemView.findViewById(R.id.time_id);
        }
    }

    public RunRecycler(List<totalRunsClass> runs) {
        this.runList = runs;
    }

    @NonNull
    @Override
    public RunViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        // to inflate layout for each row
        View row = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.run, viewGroup, false);
        RunViewHolder viewHolder = new RunViewHolder(row);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RunViewHolder holder, @SuppressLint("RecyclerView") final int position) {


        // setting data for each row by getting the position
        totalRunsClass run = runList.get(position);

        //to get the current time
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMM yyyy hh:mm zzz");
        String date = dateFormat.format(run.getStartRun());

        //to get current time
        Calendar current = Calendar.getInstance();
        current.setTimeInMillis(run.getStartRun().getTime());

        //to set the date
        holder.date.setText(date);
        //to set the day
        holder.day.setText(RunningContract.day[current.get(Calendar.DAY_OF_WEEK)]);

        //to get and set the distance
        int distance = run.getTotalDistance();
        holder.distance.setText((distance / 1000) + "." + (distance % 1000 / 100) + (distance % 100 / 10) + "km");

        //to get and set the time
        int time_taken = run.getTotalTimeTaken();
        holder.time.setText(time_taken / 60 + ":" + (time_taken % 60 < 10 ? "0" + (time_taken % 60) : (time_taken % 60)));

        //to get and set pace
        int pace = run.getPace();
        holder.pace.setText((pace / 60) + "'" + (pace % 60) + "\"");


    }

    @Override
    public int getItemCount() {
        return runList.size();
    }


}
