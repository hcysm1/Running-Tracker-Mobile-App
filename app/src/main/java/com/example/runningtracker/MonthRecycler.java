package com.example.runningtracker;

import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.runningtracker.modelClasses.RunsInMonthClass;


import java.util.ArrayList;
//SONIA MUBASHER
//20129528

public class MonthRecycler extends RecyclerView.Adapter<MonthRecycler.MonthViewHolder> {

    ArrayList<RunsInMonthClass> monthList; //arraylist for months
    RunRecycler adapter; //adapter for runs data

    public static class MonthViewHolder extends RecyclerView.ViewHolder {

        TextView monthTextView; //month
        TextView runTextView; //total runs
        TextView distanceTextView; //distance
        TextView paceTextView; //pace
        RecyclerView runRecycler; //recycler view for run data

        public MonthViewHolder(@NonNull View itemView) {
            super(itemView);

            //getting reference of elements
            monthTextView = itemView.findViewById(R.id.monthView);
            runTextView = itemView.findViewById(R.id.runView);
            distanceTextView = itemView.findViewById(R.id.distanceView);
            paceTextView = itemView.findViewById(R.id.paceView);
            runRecycler = itemView.findViewById(R.id.runRecycler);

//setting the layout manager for recycler view
            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(itemView.getContext());
            runRecycler.setLayoutManager(layoutManager);
        }
    }

    public MonthRecycler(ArrayList<RunsInMonthClass> months) {
        this.monthList = months;
    }

    @NonNull
    @Override
    public MonthViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        //inflating layout for each row
        View row = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.month, viewGroup, false);
        MonthViewHolder viewHolder = new MonthViewHolder(row);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MonthViewHolder holder, int position) {

        //setting text for view holder
        final RunsInMonthClass month = monthList.get(position);

        String runString = month.getTotalRuns() + " run(s)";
        String distanceString = month.getTotalDistanceCovered() + " km";

        holder.monthTextView.setText(month.getYearMonth()); //getting month and year
        holder.runTextView.setText(runString); //getting total runs for a month
        holder.distanceTextView.setText(distanceString); //getting total distance
        holder.paceTextView.setText(month.getPace()); //getting average pace

        //to display data for all runs as a recycler view for that month
        adapter = new RunRecycler(month.getRuns());
        holder.runRecycler.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return monthList.size();
    }
}
